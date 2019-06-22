/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.check.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.utils.CheckBillIdGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.check.entity.CheckBill;
import com.jeesite.modules.check.service.CheckBillService;

/**
 * 检测单表Controller
 * @author pengxincheng
 * @version 2019-06-22
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkBill")
public class CheckBillController extends BaseController {

	@Autowired
	private CheckBillService checkBillService;
	
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
	
}