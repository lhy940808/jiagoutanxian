package com.lhy.web.service.impl;

import com.lhy.web.helper.DataBaseHelper;
import com.lhy.web.model.Customer;
import com.lhy.web.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 优化后的客户操作实现类
 * @author liuhaiyan
 * @date 2019-12-26 14:29
 */
@Slf4j
public class CustomerServiceImplOptim implements CustomerService {
    @Override
    public List<Customer> getCustomerList() {
        List<Customer> customers = new ArrayList<>();
        String sql = "select * from customer";
        customers = DataBaseHelper.queryEntityList(Customer.class, sql);
        return customers;
    }

    @Override
    public Customer getCustomet(long id) {
        String sql = "select * from customer where id = ?";

        return DataBaseHelper.queryEntity(Customer.class, sql, id);
    }

    @Override
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DataBaseHelper.insertEntity(Customer.class, fieldMap);
    }

    @Override
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DataBaseHelper.updateEntity(Customer.class, fieldMap, id);
    }

    @Override
    public boolean deleteCustomer(long id) {
        return DataBaseHelper.deleteEntity(Customer.class, id);
    }

    private Customer buildCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setContact(rs.getString("contact"));
        customer.setEmail(rs.getString("email"));
        customer.setRemark(rs.getString("remark"));
        customer.setTelephone(rs.getString("telephone"));
        customer.setName(rs.getString("name"));
        return customer;
    }
}
