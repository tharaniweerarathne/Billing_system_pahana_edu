package com.Billing_system_pahana_edu.factory;

import com.Billing_system_pahana_edu.model.Customer;

public interface CustomerFactory {
    Customer createCustomer(String accountNo, String name, String email, String address, String telephone);
}
