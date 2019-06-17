/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.charging.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.charging.entity.ChargingItems;
import com.jeesite.modules.charging.dao.ChargingItemsDao;

/**
 * 检查项目表Service
 * @author pengxincheng
 * @version 2019-06-16
 */
@Service
@Transactional(readOnly=true)
public class ChargingItemsService extends CrudService<ChargingItemsDao, ChargingItems> {
	
	/**
	 * 获取单条数据
	 * @param chargingItems
	 * @return
	 */
	@Override
	public ChargingItems get(ChargingItems chargingItems) {
		return super.get(chargingItems);
	}
	
	/**
	 * 查询分页数据
	 * @param chargingItems 查询条件
	 * @param chargingItems.page 分页对象
	 * @return
	 */
	@Override
	public Page<ChargingItems> findPage(ChargingItems chargingItems) {
		return super.findPage(chargingItems);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param chargingItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ChargingItems chargingItems) {
		super.save(chargingItems);
	}
	
	/**
	 * 更新状态
	 * @param chargingItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ChargingItems chargingItems) {
		super.updateStatus(chargingItems);
	}
	
	/**
	 * 删除数据
	 * @param chargingItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ChargingItems chargingItems) {
		super.delete(chargingItems);
	}
	
}