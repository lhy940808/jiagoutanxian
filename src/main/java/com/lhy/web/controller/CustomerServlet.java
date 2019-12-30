package com.lhy.web.controller;

import com.lhy.web.model.Customer;
import com.lhy.web.service.CustomerService;
import com.lhy.web.service.impl.CustomerServiceImplOptim;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author liuhaiyan
 * @date 2019-12-30 17:13
 */
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = customerService.getCustomerList();
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("/WEB-INF/jsp/customer.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        customerService = new CustomerServiceImplOptim();
    }
}
