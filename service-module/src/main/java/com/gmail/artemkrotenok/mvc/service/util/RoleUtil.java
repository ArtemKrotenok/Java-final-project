package com.gmail.artemkrotenok.mvc.service.util;

import com.gmail.artemkrotenok.mvc.repository.enums.RoleEnum;

import java.util.ArrayList;
import java.util.List;

public class RoleUtil {
    public static List<String> getUserRoles() {
        List<String> userRoles = new ArrayList<>();
        for (RoleEnum role : RoleEnum.values()) {
            userRoles.add(role.name());
        }
        return userRoles;
    }
}
