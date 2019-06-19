/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.car.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.car.entity.CarType;
import com.jeesite.modules.car.dao.CarTypeDao;

/**
 * 车型表Service
 * @author pengxincheng
 * @version 2019-06-16
 */
@Service
@Transactional(readOnly=true)
public class CarTypeService extends CrudService<CarTypeDao, CarType> {
	
	/**
	 * 获取单条数据
	 * @param carType
	 * @return
	 */
	@Override
	public CarType get(CarType carType) {
		return super.get(carType);
	}
	
	/**
	 * 查询分页数据
	 * @param carType 查询条件
	 * @param carType.page 分页对象
	 * @return
	 */
	@Override
	public Page<CarType> findPage(CarType carType) {
		return super.findPage(carType);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param carType
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CarType carType) {
		super.save(carType);
	}
	
	/**
	 * 更新状态
	 * @param carType
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CarType carType) {
		super.updateStatus(carType);
	}
	
	/**
	 * 删除数据
	 * @param carType
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CarType carType) {
		super.delete(carType);
	}

	/**
	 * 获取列表
	 * @param entity
	 * @return
	 */
	@Override
	public List<CarType> findList(CarType entity) {
		return super.findList(entity);
	}
}