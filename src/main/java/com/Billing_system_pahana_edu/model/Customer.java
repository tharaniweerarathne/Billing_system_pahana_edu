package com.Billing_system_pahana_edu.model;

public class Customer {
    private String accountNo, name , email, address, telephone;

    public String getAccountNo() { return accountNo; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getTelephone() { return telephone; }

    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
