/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.charging.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.charging.entity.ChargingItems;

/**
 * 检查项目表DAO接口
 * @author pengxincheng
 * @version 2019-06-16
 */
@MyBatisDao
public interface ChargingItemsDao extends CrudDao<ChargingItems> {
	
}