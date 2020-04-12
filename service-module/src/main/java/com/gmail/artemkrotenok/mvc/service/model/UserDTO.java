package com.gmail.artemkrotenok.mvc.service.model;

import com.gmail.artemkrotenok.mvc.repository.enums.RoleEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;
    @NotNull(message = "The field 'surname' must be filled")
    @Size(min = 1, max = 40, message = "'surname' size must be between 1 and 40 characters")
    private String surname;
    @NotNull(message = "The field 'name' must be filled")
    @Size(min = 1, max = 20, message = "'name' size must be between 1 and 20 characters")
    private String name;
    @NotNull(message = "The field 'middle name' must be filled")
    @Size(min = 1, max = 40, message = "'middle name' size must be between 1 and 40 characters")
    private String middleName;
    @NotNull(message = "The field 'email' must be filled")
    @Size(min = 1, max = 50, message = "'email' size must be between 1 and 50 characters")
    private String email;
    private String password;
    private RoleEnum role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
