/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.check.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.check.entity.CheckBill;

/**
 * 检测单表DAO接口
 * @author pengxincheng
 * @version 2019-06-22
 */
@MyBatisDao
public interface CheckBillDao extends CrudDao<CheckBill> {

	
}