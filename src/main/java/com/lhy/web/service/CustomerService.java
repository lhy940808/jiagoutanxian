package com.lhy.web.service;

import com.lhy.web.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * 客户业务逻辑实现
 * @author liuhaiyan
 * @date 2019-12-25 14:38
 */
public interface CustomerService {
    /**
     * 获取客户列表
     * */
    List<Customer> getCustomerList();

    /**
     * 获取客户信息
     * */
    Customer getCustomet(long id);

    /**
     * 创建客户
     * */
    boolean createCustomer(Map<String, Object> fieldMap);

    /**
     * 更新客户
     * */
    boolean updateCustomer(long id, Map<String, Object> fieldMap);

    /**
     * 删除客户
     * */
    boolean deleteCustomer(long id);
}
