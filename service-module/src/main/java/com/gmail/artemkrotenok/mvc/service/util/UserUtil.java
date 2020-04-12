package com.gmail.artemkrotenok.mvc.service.util;

import com.gmail.artemkrotenok.mvc.repository.model.User;
import com.gmail.artemkrotenok.mvc.service.model.UserDTO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UserUtil {

    public static final int LENGTH_NEW_PASSWORD = 16;
    public static final int FROM_NUMBER_ASCII = 48;
    public static final int TO_NUMBER_ASCII = 122;
    public static final int ASCII_FILTER_1 = 57;
    public static final int ASCII_FILTER_2 = 65;
    public static final int ASCII_FILTER_3 = 90;
    public static final int ASCII_FILTER_4 = 97;

    public static String getNewPassword() {
        Random random = new Random();
        return random.ints(FROM_NUMBER_ASCII, TO_NUMBER_ASCII)
                .filter(i -> (i < ASCII_FILTER_1 || i > ASCII_FILTER_2) && (i < ASCII_FILTER_3 || i > ASCII_FILTER_4))
                .mapToObj(i -> (char) i)
                .limit(LENGTH_NEW_PASSWORD)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static Boolean sendNewPasswordToEmail(String password, String email) {
        //***************************************************************************
        //TODO here it is necessary to implement sending a password to the user email
        //***************************************************************************
        return false;
    }

    public static UserDTO getDTOFromObject(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setSurname(user.getSurname());
        userDTO.setName(user.getName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static User getObjectFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        user.setRole(userDTO.getRole());
        return user;
    }

}
