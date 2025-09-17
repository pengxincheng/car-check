/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.check.service;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import com.jeesite.modules.car.dao.CarTypeDao;
import com.jeesite.modules.car.entity.CarType;
import com.jeesite.modules.car.service.CarTypeService;
import com.jeesite.modules.charging.entity.ChargingItems;
import com.jeesite.modules.charging.service.ChargingItemsService;
import com.jeesite.modules.check.CheckBillImportListener;
import com.jeesite.modules.check.bo.CheckBillExcelModel;
import com.jeesite.modules.customer.entity.Customer;
import com.jeesite.modules.customer.service.CustomerService;
import com.jeesite.modules.enums.BillTypeEnum;
import com.jeesite.modules.utils.Idutils;
import com.jeesite.modules.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.check.entity.CheckBill;
import com.jeesite.modules.check.dao.CheckBillDao;
import com.jeesite.modules.check.entity.CheckBillItem;
import com.jeesite.modules.check.dao.CheckBillItemDao;
import org.springframework.web.multipart.MultipartFile;

/**
 * 检测单表Service
 *
 * @author pengxincheng
 * @version 2019-06-22
 */
@Service
@Transactional(readOnly = true)
public class CheckBillService extends CrudService<CheckBillDao, CheckBill> {

    @Autowired
    private CheckBillItemDao checkBillItemDao;
    @Autowired
    private CarTypeService carTypeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ChargingItemsService chargingItemsService;

    /**
     * 获取单条数据
     *
     * @param checkBill
     * @return
     */
    @Override
    public CheckBill get(CheckBill checkBill) {
        CheckBill entity = super.get(checkBill);
        if (entity != null) {
            CheckBillItem checkBillItem = new CheckBillItem(entity);
            checkBillItem.setStatus(CheckBillItem.STATUS_NORMAL);
            entity.setCheckBillItemList(checkBillItemDao.findList(checkBillItem));
        }
        return entity;
    }

    /**
     * 查询分页数据
     *
     * @param checkBill      查询条件
     * @param checkBill.page 分页对象
     * @return
     */
    @Override
    public Page<CheckBill> findPage(CheckBill checkBill) {
        return super.findPage(checkBill);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param checkBill
     */
    @Override
    @Transactional(readOnly = false)
    public void save(CheckBill checkBill) {
        super.save(checkBill);
        // 保存 CheckBill子表
        for (CheckBillItem checkBillItem : checkBill.getCheckBillItemList()) {
            if (!CheckBillItem.STATUS_DELETE.equals(checkBillItem.getStatus())) {
                checkBillItem.setBillId(checkBill);
                if (checkBillItem.getIsNewRecord()) {
                    checkBillItemDao.insert(checkBillItem);
                } else {
                    checkBillItemDao.update(checkBillItem);
                }
            } else {
                checkBillItemDao.delete(checkBillItem);
            }
        }
    }

    /**
     * 更新状态
     *
     * @param checkBill
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(CheckBill checkBill) {
        super.updateStatus(checkBill);
    }

    /**
     * 删除数据
     *
     * @param checkBill
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(CheckBill checkBill) {
        super.delete(checkBill);
        CheckBillItem checkBillItem = new CheckBillItem();
        checkBillItem.setBillId(checkBill);
        checkBillItemDao.deleteByEntity(checkBillItem);
    }

    @Override
    public long findCount(CheckBill entity) {
        entity.setCheckTime_gte(DateUtils.getMinDateOfMonth(new Date()));
        entity.setCheckTime_lte(DateUtils.getMaxDateOfMonth(new Date()));
        return super.findCount(entity);
    }

    @Transactional
    public void refundBill(CheckBill checkBill,String remark) {
        //status 1代表已经退过单了  2表示是负单不能退

        CheckBill refundBill = new CheckBill();
        BeanUtils.copyProperties(checkBill,refundBill);


        Long count = this.findCount(new CheckBill());

        //保存退单
        refundBill.setBillId(Idutils.getNextCheckBillId(count.intValue()));
        refundBill.setId(null);
        refundBill.setBillType(BillTypeEnum.REFUND_BILL.getCode());
        refundBill.setRemarks(remark);
        refundBill.setTotalAmt(refundBill.getTotalAmt() * -1);
        refundBill.getCheckBillItemList().forEach(c -> {
            c.setBillId(refundBill);
            c.setPrice(c.getPrice() * -1);
            c.setId(null);
        });
        this.save(refundBill);

        //修改原单状态
        checkBill.setBillType(BillTypeEnum.HAS_REFUNDED.getCode());
        this.update(checkBill);

    }

    @Override
    public List<CheckBill> findList(CheckBill entity) {
        return super.findList(entity);
    }



    @Transactional(rollbackFor = Exception.class)
    public void importBill(MultipartFile file) throws IOException {

        List<CheckBillExcelModel> billList = EasyExcel.read(file.getInputStream(), CheckBillExcelModel.class, new CheckBillImportListener()).doReadAllSync();

        // 判断是否有已存在的检测单
        CheckBill checkBillParam = new CheckBill();
        checkBillParam.setBillIdIn(billList.stream().map(CheckBillExcelModel::getBillId).toArray(String[]::new));
        List<CheckBill> dbCheckBills = this.findList(checkBillParam);
        List<String> existBillIds = dbCheckBills.stream().map(CheckBill::getBillId).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(existBillIds)){
            throw new RuntimeException("以下检测单已存在，请从excel删除后重新导入:" + String.join(",", existBillIds));
        }

        List<String> refundBillIds = billList.stream()
                .filter(c -> c.getPrice() < 0D)
                .map(CheckBillExcelModel::getBillId).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(refundBillIds)){
            throw new RuntimeException("存在退款单无法导入，请从excel删除后重新导入:" + String.join(",", refundBillIds));
        }



        // 车型处理
        List<String> allCarType = billList.stream().map(CheckBillExcelModel::getCarType).collect(Collectors.toList());
        //查看是否存在车型，没新增
        List<String> existCarType = carTypeService.findExistCarTypeList();
        allCarType.removeAll(existCarType);
        if(CollectionUtils.isNotEmpty(allCarType)){
            carTypeService.batchSave(allCarType);
        }


        // 查看是否存在客户，没有新增
        Customer customerParam = new Customer();
        customerParam.setCustomerCodeIn(billList.stream().map(CheckBillExcelModel::getCustomerId).toArray(String[]::new));
        List<Customer> dbcustomerList = customerService.findList(customerParam);
        List<String> dbCodeList = dbcustomerList.stream().map(Customer::getCode).collect(Collectors.toList());

        List<CheckBillExcelModel> needAddCustomerList = billList.stream().filter(b-> !dbCodeList.contains(b.getCustomerId())).collect(Collectors.toList());
        customerService.batchSaveFromExcel(needAddCustomerList);

        //获取全部的检测项目
        List<ChargingItems> chargingItems = chargingItemsService.findList(new ChargingItems());
        Map<String, ChargingItems> chargingItemsMap = chargingItems.stream()
                .filter(c -> Objects.equals(c.getStatus(),"0"))
                .collect(Collectors.toMap(ChargingItems::getItemName, Function.identity(), (v1, v2) -> v2));

        List<String> noChargeItemList = billList.stream()
                .map(CheckBillExcelModel::getItemName)
                .filter(name -> !chargingItemsMap.containsKey(name)).collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(noChargeItemList)){
            throw new RuntimeException("以下检测项目不存在，请先新增后再导入，保证检测项目名字一致。需要新增的检测项目：" + String.join(",", noChargeItemList));
        }


        Map<String,List<CheckBillExcelModel>> billIdBillListMap = billList.stream().collect(Collectors.groupingBy(CheckBillExcelModel::getBillId));
        billIdBillListMap.forEach((billId, excelList) -> {
            if(CollectionUtils.isEmpty(excelList)) {
               return;
            }


            CheckBillExcelModel oneExcelBill = excelList.get(0);

            Double totalAmt = excelList.stream()
                    .map(CheckBillExcelModel::getPrice)
                    .filter(Objects::nonNull)
                    .reduce(0.00, Double::sum);

            CheckBill checkBill = new CheckBill();
            checkBill.setBillId(billId);
            checkBill.setCheckTime(DateUtils.parseDate(oneExcelBill.getCheckTime()));
            checkBill.setPlateNumber(oneExcelBill.getPlateNumber());
            checkBill.setCustomerId(oneExcelBill.getCustomerId());
            checkBill.setCustomerName(oneExcelBill.getCustomerName());
            checkBill.setCustomerPhoneNumber(oneExcelBill.getCustomerPhoneNumber());
            checkBill.setCustomerAddress(oneExcelBill.getCustomerAddress());
            checkBill.setTotalAmt(totalAmt);
            checkBill.setBillType(BillTypeEnum.NOMAL_BILL.getCode());
            checkBill.setCarType(oneExcelBill.getCarType());


            List<CheckBillItem> checkBillItems = Lists.newArrayList();

            excelList.forEach(eb ->{
                ChargingItems charge = chargingItemsMap.get(eb.getItemName());

                CheckBillItem checkBillItem = new CheckBillItem();
                checkBillItem.setItemId(charge.getCode());
                checkBillItem.setItemName(charge.getItemName());
                checkBillItem.setNum(eb.getNum());
                checkBillItem.setPrice(eb.getPrice());
                checkBillItem.setRemarks(eb.getRemark());
                checkBillItems.add(checkBillItem);
            });
            checkBill.setCheckBillItemList(checkBillItems);

            this.save(checkBill);

        });

    }
}
