/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.check.service;

import java.util.Date;
import java.util.List;

import com.jeesite.modules.enums.BillTypeEnum;
import com.jeesite.modules.utils.CheckBillIdGenerator;
import com.jeesite.modules.utils.DateUtils;
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
    public void refundBill(CheckBill checkBill) {
        //status 1代表已经退过单了  2表示是负单不能退

        CheckBill refundBill = new CheckBill();
        BeanUtils.copyProperties(checkBill,refundBill);


        Long count = this.findCount(new CheckBill());

        //保存退单
        refundBill.setBillId(CheckBillIdGenerator.getNextId(count.intValue()));
        refundBill.setId(null);
        refundBill.setBillType(BillTypeEnum.REFUND_BILL.getCode());

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
}