/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.customer.entity.Customer;
import com.jeesite.modules.customer.dao.CustomerDao;

/**
 * 客户Service
 * @author pengxincheng
 * @version 2019-06-18
 */
@Service
@Transactional(readOnly=true)
public class CustomerService extends CrudService<CustomerDao, Customer> {
	
	/**
	 * 获取单条数据
	 * @param customer
	 * @return
	 */
	@Override
	public Customer get(Customer customer) {
		return super.get(customer);
	}
	
	/**
	 * 查询分页数据
	 * @param customer 查询条件
	 * @param customer.page 分页对象
	 * @return
	 */
	@Override
	public Page<Customer> findPage(Customer customer) {
		return super.findPage(customer);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param customer
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Customer customer) {
		super.save(customer);
	}
	
	/**
	 * 更新状态
	 * @param customer
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Customer customer) {
		super.updateStatus(customer);
	}
	
	/**
	 * 删除数据
	 * @param customer
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Customer customer) {
		super.delete(customer);
	}


	@Override
	public long findCount(Customer entity) {
		return super.findCount(entity);
	}


	/**
	 * 更新
	 * @param customerCode
	 * @param customerName
	 * @param customerAddress
	 * @param agentName
	 * @param remarkName
	 */
	@Transactional(readOnly=false)
	public void updateByCode(String customerCode,String customerName, String customerAddress, String agentName,String remarkName){
		Customer param = new Customer();
		param.setCode(customerCode);


		Customer customer = new Customer();
		customer.setAgentName(agentName);
		customer.setRemarkName(remarkName);
		customer.setName(customerName);
		customer.setAddress(customerAddress);

		super.dao.updateByEntity(customer,param);
	}
}