package com.ukma.mylibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ukma.mylibrary.entities.filters.EmptyIdFilter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Entity {
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = EmptyIdFilter.class)
    private long id;
    private String name;
    private String surname;
    @JsonProperty("phone_num")
    private String phoneNum;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Role role = null;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String password;

    public User() {
    }

    public User(long id, String name, String surname, String phoneNum, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.role = role;
    }

    public User(String name, String surname, String phoneNum, Role role) {
        this.name = name;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.role = role;
    }

    public User(String name, String surname, String phoneNum, Role role, String password) {
        this.name = name;
        this.surname = surname;
        this.phoneNum = phoneNum;
        this.role = role;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", role=" + role +
                '}';
    }
}
