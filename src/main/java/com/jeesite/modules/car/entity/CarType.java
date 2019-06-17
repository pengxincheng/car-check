/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.car.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 车型表Entity
 * @author pengxincheng
 * @version 2019-06-16
 */
@Table(name="car_type", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="type_name", attrName="typeName", label="车型", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class CarType extends DataEntity<CarType> {
	
	private static final long serialVersionUID = 1L;
	private String typeName;		// 车型
	
	public CarType() {
		this(null);
	}

	public CarType(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="车型长度不能超过 64 个字符")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}