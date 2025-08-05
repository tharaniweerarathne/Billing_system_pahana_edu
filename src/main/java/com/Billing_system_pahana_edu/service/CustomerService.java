package com.Billing_system_pahana_edu.service;

import com.Billing_system_pahana_edu.dao.CustomerDAO;
import com.Billing_system_pahana_edu.model.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDAO dao = new CustomerDAO();

    public void addCustomer(Customer c) { dao.addCustomer(c); }
    public void updateCustomer(Customer c) { dao.updateCustomer(c); }
    public void deleteCustomer(String accountNo) { dao.deleteCustomer(accountNo); }
    public List<Customer> getAll() { return dao.getAll(); }
    public String getNextAccountNo() {
        return dao.getNextAccountNo();
    }

    public List<Customer> searchCustomers(String keyword) {
        return dao.searchCustomers(keyword);
    }
}
