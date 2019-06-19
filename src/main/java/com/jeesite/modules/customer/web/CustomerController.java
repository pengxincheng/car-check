/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.customer.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.car.entity.CarType;
import com.jeesite.modules.car.service.CarTypeService;
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
import com.jeesite.modules.customer.entity.Customer;
import com.jeesite.modules.customer.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户表Controller
 * @author pengxincheng
 * @version 2019-06-18
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CarTypeService carTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Customer get(String id, boolean isNewRecord) {
		return customerService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("customer:customer:view")
	@RequestMapping(value = {"list", ""})
	public String list(Customer customer, Model model) {
		model.addAttribute("customer", customer);
		return "modules/customer/customerList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("customer:customer:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Customer> listData(Customer customer, HttpServletRequest request, HttpServletResponse response) {
		customer.setPage(new Page<>(request, response));
		Page<Customer> page = customerService.findPage(customer);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("customer:customer:view")
	@RequestMapping(value = "form")
	public String form(Customer customer, Model model) {
		List<CarType> carTypeList = carTypeService.findList(new CarType());
		model.addAttribute("customer", customer);
		model.addAttribute("carTypeList", carTypeList);
		return "modules/customer/customerForm";
	}

	/**
	 * 保存客户表
	 */
	@RequiresPermissions("customer:customer:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Customer customer) {
		customerService.save(customer);
		return renderResult(Global.TRUE, text("保存客户表成功！"));
	}
	
	/**
	 * 删除客户表
	 */
	@RequiresPermissions("customer:customer:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Customer customer) {
		customerService.delete(customer);
		return renderResult(Global.TRUE, text("删除客户表成功！"));
	}
	
}