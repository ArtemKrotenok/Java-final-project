package com.gmail.artemkrotenok.service.impl;

import com.gmail.artemkrotenok.service.PasswordService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordServiceImpl implements PasswordService {

    public static final int LENGTH_PASSWORD = 10;
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String getNewPassword() {
        StringBuilder result = new StringBuilder(LENGTH_PASSWORD);
        for (int i = 0; i < LENGTH_PASSWORD; i++) {
            int rndCharAt = secureRandom.nextInt(PASSWORD_ALLOW_BASE.length());
            char rndChar = PASSWORD_ALLOW_BASE.charAt(rndCharAt);
            result.append(rndChar);
        }
        return result.toString();
    }

    @Override
    public Boolean sendPasswordToEmail(String password, String email) {
        //***************************************************************************
        //TODO here it is necessary to implement sending a password to the user email
        //***************************************************************************
        return true;
    }
}
