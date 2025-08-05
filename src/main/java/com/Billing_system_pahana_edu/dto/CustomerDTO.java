package com.Billing_system_pahana_edu.dto;

public class CustomerDTO {
    private String name, email , address, telephone;

    public CustomerDTO() {}
    public CustomerDTO(String name, String email, String address, String telephone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getTelephone() { return telephone; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}

