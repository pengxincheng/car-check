/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.customer.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.car.entity.CarType;
import com.jeesite.modules.car.service.CarTypeService;
import com.jeesite.modules.customer.dao.CustomerDao;
import com.jeesite.modules.customer.entity.Customer;
import com.jeesite.modules.customer.service.CustomerService;
import com.jeesite.modules.enums.ApplyTypeEnum;
import com.jeesite.modules.utils.DateUtils;
import com.jeesite.modules.utils.Idutils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 客户Controller
 * @author pengxincheng
 * @version 2019-06-18
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customer")
public class CustomerController extends BaseController {

	private static final String SELECTED = "\uF0FE";

	private static final String UN_SELECTED = "□";

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CarTypeService carTypeService;
	@Autowired
	private CustomerDao customerDao;

	@Value("${localPlate}")
	private String localPlate;
	
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
	 * 查询列数据
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
		if(StringUtils.isEmpty(customer.getCode())){
			Long count = customerService.findCount(new Customer());
			customer.setCode(Idutils.getNextCustomerCode(count.intValue()));
		}
		try{
			customerService.save(customer);
		}catch (DuplicateKeyException e){
			return renderResult(Global.FALSE, text("添加客户失败，该车牌号已存在"));
		}

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

	/**
	 * 根据车牌查询
	 */
	@RequiresPermissions("customer:customer:getByPlateNum")
	@RequestMapping(value = "getByPlateNum")
	@ResponseBody
	public String getByPlateNum(Customer customer) {
		List<Customer> customers = customerService.findList(customer);
		if (null != customers && customers.size() > 0) {
			return renderResult(Global.TRUE, text("根据车牌号查询成功"), customers.get(0));
		}else {
			return renderResult(Global.FALSE,text("客户不存在请先添加"));
		}

	}


	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("customer:customer:view")
	@RequestMapping(value = "/print")
	public String form(@RequestParam String customerCode,Model model) {
		Customer customer = new Customer();
		customer.setCode(customerCode);
		Customer dbCustomer = customerDao.getByEntity(customer);
		if (Objects.isNull(dbCustomer)) {
			return renderResult(Global.FALSE, text("未查询到客户信息"));
		}
		if(StringUtils.isNotBlank(dbCustomer.getAgentName())){
			dbCustomer.setAgentPhone(dbCustomer.getPhoneNumber());
		}
		dbCustomer.setDate(DateUtils.getStringFromDate(new Date(),DateUtils.DATE_TIME_ZH_CN));

		if (dbCustomer.getPlateNumber().contains(localPlate)) {
			dbCustomer.setLocal(SELECTED);
			dbCustomer.setNotLocal(UN_SELECTED);
		} else {
			dbCustomer.setLocal(UN_SELECTED);
			dbCustomer.setNotLocal(SELECTED);
		}

		model.addAttribute("customer", dbCustomer);
		return "modules/print/applyBillv2";
	}
	
}