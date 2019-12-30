package com.lhy.web.service.impl;

import com.lhy.web.model.Customer;
import com.lhy.web.service.CustomerService;
import com.lhy.web.utils.PropsUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 客户的增删改查操作
 * @author liuhaiyan
 * @date 2019-12-25 14:44
 */
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties jdbc = PropsUtil.loadProps("jdbc.properties");
        DRIVER = jdbc.getProperty("jdbc.driver");
        URL = jdbc.getProperty("jdbc.url");
        USERNAME = jdbc.getProperty("jdbc.username");
        PASSWORD = jdbc.getProperty("jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("can not load jdbc driver ", e);
        }
    }

    @Override
    public List<Customer> getCustomerList() {
        Connection conn = null;
        List<Customer> customers = new ArrayList<Customer>();
        try {
            String sql = "select * from customer";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Customer customer = buildCustomer(result);
                customers.add(customer);
            }
        } catch (SQLException e) {
            log.error("execute sql failure", e);
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException se) {
                    log.error("close connection failure", se);
                }
            }
        }
        return customers;
    }

    @Override
    public Customer getCustomet(long id) {
        return null;
    }

    @Override
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return false;
    }

    @Override
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return false;
    }

    @Override
    public boolean deleteCustomer(long id) {
        return false;
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
