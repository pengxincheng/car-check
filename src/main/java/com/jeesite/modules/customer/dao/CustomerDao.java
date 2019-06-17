/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.customer.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.customer.entity.Customer;

/**
 * 客户表DAO接口
 * @author pengxincheng
 * @version 2019-06-16
 */
@MyBatisDao
public interface CustomerDao extends CrudDao<Customer> {
	
}