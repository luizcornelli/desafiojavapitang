package com.desafiojavapitang.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponseCreate {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String phone;
    private String createdAt;
    private String lastLogin;

    private List<CarResponse> cars = new ArrayList<>();

    public UserResponseCreate(){
    }

    public UserResponseCreate(Long id, String firstName, String lastName, String email, String birthday, String phone, String createdAt, String lastLogin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.phone = phone;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<CarResponse> getCars() {
        return cars;
    }
}
