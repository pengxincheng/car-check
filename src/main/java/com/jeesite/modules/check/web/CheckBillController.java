/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.check.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.car.entity.CarType;
import com.jeesite.modules.car.service.CarTypeService;
import com.jeesite.modules.check.bo.CheckBillExcelModel;
import com.jeesite.modules.check.bo.CheckBillItemBO;
import com.jeesite.modules.check.entity.CheckBillItem;
import com.jeesite.modules.check.service.CheckBillItemService;
import com.jeesite.modules.check.vo.CheckBillStatisticsVo;
import com.jeesite.modules.enums.BillTypeEnum;
import com.jeesite.modules.utils.DateUtils;
import com.jeesite.modules.utils.Idutils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.check.entity.CheckBill;
import com.jeesite.modules.check.service.CheckBillService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 检测单表Controller
 *
 * @author pengxincheng
 * @version 2019-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkBill")
public class CheckBillController extends BaseController {

    @Autowired
    private CheckBillService checkBillService;
    @Autowired
    private CheckBillItemService checkBillItemService;
    @Autowired
    private CarTypeService carTypeService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public CheckBill get(String id, boolean isNewRecord) {
        return checkBillService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("check:checkBill:view")
    @RequestMapping(value = {"list", ""})
    public String list(CheckBill checkBill, Model model) {
        model.addAttribute("checkBill", checkBill);
        return "modules/check/checkBillList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("check:checkBill:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<CheckBill> listData(CheckBill checkBill, HttpServletRequest request, HttpServletResponse response) {
       /* if(Objects.isNull(checkBill.getCheckTime_gte())){
            checkBill.setCheckTime_gte(DateUtils.getMinDateOfDay(new Date()));
        }
        if(Objects.isNull(checkBill.getCheckTime_lte())){
            checkBill.setCheckTime_lte(DateUtils.getMaxDateOfDay(new Date()));
        }*/
        checkBill.setPage(new Page<>(request, response));
        Page<CheckBill> page = checkBillService.findPage(checkBill);
        return page;
    }

    /**
     * 编辑表单
     */
    @RequiresPermissions("check:checkBill:view")
    @RequestMapping(value = "form")
    public String form(CheckBill checkBill, Model model) {
        Long count = checkBillService.findCount(new CheckBill());
        checkBill.setBillId(Idutils.getNextCheckBillId(count.intValue()));

        List<CarType> carTypeList = carTypeService.findList(new CarType());
        model.addAttribute("carTypeList", carTypeList);

        model.addAttribute("checkBill", checkBill);
        return "modules/check/checkBillForm";
    }

    /**
     * 查看表单
     */
    @RequiresPermissions("check:checkBill:view")
    @RequestMapping(value = "detail")
    public String detail(CheckBill checkBill, Model model) {
        model.addAttribute("checkBill", checkBill);
        return "modules/check/checkBillDetail";
    }

    @RequiresPermissions("check:checkBill:view")
    @GetMapping("/getDetail")
    public CheckBill getDetail(@RequestParam("id") String id) {
        CheckBill param = new CheckBill();
        param.setId(id);
        CheckBill c = checkBillService.get(param);
        return c;
    }

    @RequiresPermissions("check:checkBill:view")
    @RequestMapping("/print")
    public String print(@RequestParam("id") String id, Model model) {
        DecimalFormat df = new DecimalFormat("#0.00");
        CheckBill param = new CheckBill();
        param.setId(id);
        CheckBill c = checkBillService.get(param);
        c.setTotalAmtStr(df.format(c.getTotalAmt()));

      /*  if(BillTypeEnum.SETTLED.getCode() == c.getBillType()){
            return renderResult(Global.TRUE, text("该单已结算过"));
        }*/

        model.addAttribute("checkBill", c);

        List<CheckBillItemBO> checkBillItemBOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(c.getCheckBillItemList())) {
            c.getCheckBillItemList().forEach(item -> {
                CheckBillItemBO checkBillItemBO = new CheckBillItemBO();
                checkBillItemBO.setItemId(item.getItemId());
                checkBillItemBO.setItemName(item.getItemName());
                checkBillItemBO.setNum(item.getNum());
                checkBillItemBO.setPrice(df.format(item.getPrice()));
                checkBillItemBO.setRemarks(c.getRemarks());
                checkBillItemBOList.add(checkBillItemBO);
            });
        }
        //c.setBillType(BillTypeEnum.SETTLED.getCode());
        //  checkBillService.update(c);
        model.addAttribute("checkBillItemList", checkBillItemBOList);
        return "modules/print/sheet001";
    }

    /**
     * 保存检测单表
     */
    @RequiresPermissions("check:checkBill:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated CheckBill checkBill) {
        checkBillService.save(checkBill);
        return renderResult(Global.TRUE, text("保存检测单表成功！"));
    }

    /**
     * 删除检测单表
     */
    @RequiresPermissions("check:checkBill:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(CheckBill checkBill) {
        checkBillService.delete(checkBill);
        return renderResult(Global.TRUE, text("删除检测单表成功！"));
    }

    /**
     * 导出Excell
     *
     * @param request
     * @param response
     * @param checkBill
     */
    //todo 优化导出文件名
    @GetMapping("/export/excel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, CheckBill checkBill) {
        try {
            String fileName = new String("检测单-".getBytes(), "UTF-8") + DateUtils.getStringFromDate(checkBill.getCheckTime_gte(), DateUtils.FORMAT_DATE) +
                    "_" + DateUtils.getStringFromDate(checkBill.getCheckTime_lte(), DateUtils.FORMAT_DATE);
            List<CheckBill> checkBills = checkBillService.findList(checkBill);

            List<CheckBillExcelModel> checkBillExcelModels = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(checkBills)) {
                checkBills.forEach(cb -> {
                    CheckBillItem checkBillItem = new CheckBillItem();
                    checkBillItem.setBillId(cb);
                    List<CheckBillItem> checkBillItems = checkBillItemService.findList(checkBillItem);

                    if (CollectionUtils.isNotEmpty(checkBillItems)) {
                        checkBillItems.forEach(cbi -> {
                            CheckBillExcelModel excelModel = new CheckBillExcelModel();
                            BeanUtils.copyProperties(cb, excelModel, "checkTime");
                            BeanUtils.copyProperties(cbi, excelModel);
                            excelModel.setCheckTime(DateUtils.getStringFromDate(cb.getCheckTime(), DateUtils.FORMAT_DATE_TIME));
                            excelModel.setRemark(cb.getRemarks());
                            checkBillExcelModels.add(excelModel);
                        });
                    }
                });
            }

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            Sheet sheet1 = new Sheet(1, 0, CheckBillExcelModel.class);
            sheet1.setSheetName("第一个sheet");
            writer.write(checkBillExcelModels, sheet1);
            writer.finish();
            out.flush();
        } catch (Exception e) {

        } finally {

        }
    }

    @RequestMapping("/refund")
    @ResponseBody
    public String refundBill(@Param("checkBillId") String checkBillId, @Param("remark") String remark) {
        CheckBill checkBill = checkBillService.get(new CheckBill(checkBillId));

        if (checkBill.getBillType() == BillTypeEnum.REFUND_BILL.getCode()) {
            return renderResult(Global.FALSE, text("该单为负单，不能再退"));
        }

        if (checkBill.getBillType() == BillTypeEnum.HAS_REFUNDED.getCode()) {
            return renderResult(Global.FALSE, "该单已经退过单，不能再退");
        }

        checkBill.setRemarks(remark);
        checkBillService.refundBill(checkBill);

        return renderResult(Global.TRUE, text("退单成功！"));
    }


    /**
     * 查询列表数据
     *
     * @param checkBill
     * @return
     */
    @RequiresPermissions("check:checkBill:view")
    @RequestMapping(value = "getStatistics")
    @ResponseBody
    public CheckBillStatisticsVo getStatistics(CheckBill checkBill) {
        if (Objects.isNull(checkBill.getCheckTime_gte())) {
            checkBill.setCheckTime_gte(DateUtils.getMinDateOfDay(new Date()));
        }
        if (Objects.isNull(checkBill.getCheckTime_lte())) {
            checkBill.setCheckTime_lte(DateUtils.getMaxDateOfDay(new Date()));
        }
        List<CheckBill> checkBills = checkBillService.findList(checkBill);

        CheckBillStatisticsVo result = new CheckBillStatisticsVo();
        if (CollectionUtils.isNotEmpty(checkBills)) {
            Double d = checkBills.stream().collect(Collectors.summingDouble(CheckBill::getTotalAmt));
            BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
            result.setTotalCount(checkBills.size());
            result.setTotalAmt(bg.doubleValue());
        }
        return result;
    }
}
