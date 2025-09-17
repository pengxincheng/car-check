/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.test;

import com.jeesite.modules.Application;
import com.jeesite.modules.check.bo.CheckBillExcelModel;
import com.jeesite.modules.customer.entity.Customer;
import com.jeesite.modules.customer.service.CustomerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 初始化核心表数据
 * @author ThinkGem
 * @version 2017-10-22
 */

@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class SqlTest {

	@Resource
	private CustomerService customerService;

	@Test
	public void testCustomerFindList(){


		Customer customer = new Customer();
		customer.setCustomerCodeIn(new String[]{"K00000001","2"});

		List<Customer> customerList = customerService.findList(customer);

		Assert.assertNotNull(customerList);
	}


}
