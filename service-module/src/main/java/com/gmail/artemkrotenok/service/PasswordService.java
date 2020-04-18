package com.gmail.artemkrotenok.service;

public interface PasswordService {

    String getNewPassword();

    Boolean sendPasswordToEmail(String password, String email);
}
