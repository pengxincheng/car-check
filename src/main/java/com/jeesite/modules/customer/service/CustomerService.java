/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.customer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.beust.jcommander.internal.Sets;
import com.google.common.collect.Lists;
import com.jeesite.modules.check.bo.CheckBillExcelModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private CustomerDao customerDao;

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

	public void batchSave(List<Customer> customers){
		super.dao.insertBatch(customers);
	}

	/**
	 * 按照code列表查询
	 * @param customerCodeList
	 * @return
	 */
	List<Customer> queryByCode(List<String> customerCodeList){
		List<Customer> customerList = Lists.newArrayList();
		if(CollectionUtils.isEmpty(customerCodeList)){
			return customerList;
		}



		return customerList;
	}

	/**
	 * 批量插入
	 * @param needAddCustomerList
	 */
	public void batchSaveFromExcel(List<CheckBillExcelModel> needAddCustomerList) {
		if(CollectionUtils.isEmpty(needAddCustomerList)){
			return;
		}

		Set<String> plateNumSet = Sets.newHashSet();

		List<Customer> customerList = Lists.newArrayList();
		needAddCustomerList.forEach(c ->{
			if(plateNumSet.contains(c.getPlateNumber())){
				return;
			}

			plateNumSet.add(c.getPlateNumber());
			Customer customer = new Customer();
			customer.setCode(c.getCustomerId());
			customer.setPlateNumber(c.getPlateNumber());
			customer.setName(c.getCustomerName());
			customer.setCarType(c.getCarType());
			customer.setAddress(c.getCustomerAddress());
			customer.setPhoneNumber(c.getCustomerPhoneNumber());
			customer.setRemarkName(c.getRemarkName());
			customer.setRemarks(c.getRemark());
			customer.setAgentName(c.getAgentName());
			customerList.add(customer);
		});

		if(CollectionUtils.isNotEmpty(customerList)){
			dao.insertBatch(customerList);
		}

	}
}
