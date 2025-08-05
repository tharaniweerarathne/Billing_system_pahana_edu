package com.Billing_system_pahana_edu.mapper;

import com.Billing_system_pahana_edu.dto.CustomerDTO;
import com.Billing_system_pahana_edu.model.Customer;

public class CustomerMapper {
    public static Customer fromDTO(CustomerDTO dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setAddress(dto.getAddress());
        c.setTelephone(dto.getTelephone());
        return c;
    }
}
