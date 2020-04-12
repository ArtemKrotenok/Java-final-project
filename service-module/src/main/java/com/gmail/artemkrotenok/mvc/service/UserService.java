package com.gmail.artemkrotenok.mvc.service;

import com.gmail.artemkrotenok.mvc.service.model.AppUserPrincipal;
import com.gmail.artemkrotenok.mvc.service.model.UserDTO;

import java.util.List;

public interface UserService {

    void add(UserDTO userDTO);

    UserDTO getUserByEmail(String email);

    AppUserPrincipal getCurrentUser();

    Long getCountUsers();

    List<UserDTO> getItemsByPage(int page);

    UserDTO getUserById(Long id);

    void changeUserRole(UserDTO userDTO);

    List<UserDTO> getItemsByPageSorted(int page);

    void deleteUsersByIds(Long[] userIds);

    void changeUserPassword(Long id);
}
