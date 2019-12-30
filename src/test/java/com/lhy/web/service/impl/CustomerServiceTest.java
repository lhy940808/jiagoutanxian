package com.lhy.web.service.impl;

import com.lhy.web.helper.DataBaseHelper;
import com.lhy.web.model.Customer;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 单元测试
 * @author liuhaiyan
 * @date 2019-12-25 20:12
 */
public class CustomerServiceTest {
    private CustomerServiceImpl customerService;
    private CustomerServiceImplOptim customerServiceImplOptim;

    public CustomerServiceTest() {
        customerService = new CustomerServiceImpl();
        customerServiceImplOptim = new CustomerServiceImplOptim();
    }

    @Before
    public void init() throws Exception{
        DataBaseHelper.executeSqlFIle("sql/customer.sql");


    }

    @Test
    public void getCustomerListTest() throws Exception {
        List<Customer> customers = customerService.getCustomerList();
        Assert.assertEquals(2, customers.size());
    }

    @Test
    public void getCustomerTest() throws Exception {
        Customer customer = customerServiceImplOptim.getCustomet(1);
        Assert.assertEquals("jack_test", customer.getName());
    }


}
