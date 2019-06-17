/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.charging.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.charging.entity.ChargingItems;
import com.jeesite.modules.charging.service.ChargingItemsService;

/**
 * 检查项目表Controller
 * @author pengxincheng
 * @version 2019-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/charging/chargingItems")
public class ChargingItemsController extends BaseController {

	@Autowired
	private ChargingItemsService chargingItemsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ChargingItems get(String id, boolean isNewRecord) {
		return chargingItemsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("charging:chargingItems:view")
	@RequestMapping(value = {"list", ""})
	public String list(ChargingItems chargingItems, Model model) {
		model.addAttribute("chargingItems", chargingItems);
		return "modules/charging/chargingItemsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("charging:chargingItems:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ChargingItems> listData(ChargingItems chargingItems, HttpServletRequest request, HttpServletResponse response) {
		chargingItems.setPage(new Page<>(request, response));
		Page<ChargingItems> page = chargingItemsService.findPage(chargingItems);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("charging:chargingItems:view")
	@RequestMapping(value = "form")
	public String form(ChargingItems chargingItems, Model model) {
		model.addAttribute("chargingItems", chargingItems);
		return "modules/charging/chargingItemsForm";
	}

	/**
	 * 保存检查项目表
	 */
	@RequiresPermissions("charging:chargingItems:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ChargingItems chargingItems) {
		chargingItemsService.save(chargingItems);
		return renderResult(Global.TRUE, text("保存检查项目表成功！"));
	}
	
	/**
	 * 删除检查项目表
	 */
	@RequiresPermissions("charging:chargingItems:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ChargingItems chargingItems) {
		chargingItemsService.delete(chargingItems);
		return renderResult(Global.TRUE, text("删除检查项目表成功！"));
	}
	
}