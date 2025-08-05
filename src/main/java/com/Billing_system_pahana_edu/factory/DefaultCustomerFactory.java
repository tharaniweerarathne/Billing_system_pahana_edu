package com.Billing_system_pahana_edu.factory;

import com.Billing_system_pahana_edu.model.Customer;

public class DefaultCustomerFactory implements CustomerFactory {
    @Override
    public Customer createCustomer(String accountNo, String name, String email, String address, String telephone) {
        Customer c = new Customer();
        c.setAccountNo(accountNo);
        c.setName(name);
        c.setEmail(email);
        c.setAddress(address);
        c.setTelephone(telephone);
        return c;
    }
}