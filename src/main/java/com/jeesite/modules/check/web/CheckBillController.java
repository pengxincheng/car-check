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
import com.jeesite.modules.check.bo.CheckBillExcelModel;
import com.jeesite.modules.check.entity.CheckBillItem;
import com.jeesite.modules.check.service.CheckBillItemService;
import com.jeesite.modules.utils.CheckBillIdGenerator;
import org.apache.commons.collections4.CollectionUtils;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        checkBill.setPage(new Page<>(request, response));
        Page<CheckBill> page = checkBillService.findPage(checkBill);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("check:checkBill:view")
    @RequestMapping(value = "form")
    public String form(CheckBill checkBill, Model model) {
        Long count = checkBillService.findCount(new CheckBill());
        checkBill.setBillId(CheckBillIdGenerator.getNextId(count.intValue()));
        model.addAttribute("checkBill", checkBill);
        return "modules/check/checkBillForm";
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

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, CheckBill checkBill) {
        try {
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
                            BeanUtils.copyProperties(cb,excelModel);
                            BeanUtils.copyProperties(cbi,excelModel);
                            checkBillExcelModels.add(excelModel);
                        });
                    }
                });
            }

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + "test" + ".xlsx");
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            String fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                    .getBytes(), "UTF-8");
            Sheet sheet1 = new Sheet(1, 0, CheckBillExcelModel.class);
            sheet1.setSheetName("第一个sheet");
            writer.write(checkBillExcelModels, sheet1);
            writer.finish();
            out.flush();
        } catch (Exception e) {

        }
    }
}
