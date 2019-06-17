/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.car.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.car.entity.CarType;

/**
 * 车型表DAO接口
 * @author pengxincheng
 * @version 2019-06-16
 */
@MyBatisDao
public interface CarTypeDao extends CrudDao<CarType> {
	
}