package com.Billing_system_pahana_edu.mapper;

import com.Billing_system_pahana_edu.dto.CustomerDTO;
import com.Billing_system_pahana_edu.model.Customer;

public class CustomerMapper {
    public static Customer fromDTO(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setTelephone(dto.getTelephone());
        return customer;
    }


    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setTelephone(customer.getTelephone());
        return dto;
    }
}