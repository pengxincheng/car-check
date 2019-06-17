/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.car.web;

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
import com.jeesite.modules.car.entity.CarType;
import com.jeesite.modules.car.service.CarTypeService;

/**
 * 车型表Controller
 * @author pengxincheng
 * @version 2019-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carType")
public class CarTypeController extends BaseController {

	@Autowired
	private CarTypeService carTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CarType get(String id, boolean isNewRecord) {
		return carTypeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("car:carType:view")
	@RequestMapping(value = {"list", ""})
	public String list(CarType carType, Model model) {
		model.addAttribute("carType", carType);
		return "modules/car/carTypeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("car:carType:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CarType> listData(CarType carType, HttpServletRequest request, HttpServletResponse response) {
		carType.setPage(new Page<>(request, response));
		Page<CarType> page = carTypeService.findPage(carType);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("car:carType:view")
	@RequestMapping(value = "form")
	public String form(CarType carType, Model model) {
		model.addAttribute("carType", carType);
		return "modules/car/carTypeForm";
	}

	/**
	 * 保存车型表
	 */
	@RequiresPermissions("car:carType:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated CarType carType) {
		carTypeService.save(carType);
		return renderResult(Global.TRUE, text("保存车型表成功！"));
	}
	
	/**
	 * 删除车型表
	 */
	@RequiresPermissions("car:carType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CarType carType) {
		carTypeService.delete(carType);
		return renderResult(Global.TRUE, text("删除车型表成功！"));
	}
	
}