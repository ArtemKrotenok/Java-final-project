package com.gmail.artemkrotenok.mvc.service.impl;

import com.gmail.artemkrotenok.mvc.repository.UserRepository;
import com.gmail.artemkrotenok.mvc.repository.model.User;
import com.gmail.artemkrotenok.mvc.service.UserService;
import com.gmail.artemkrotenok.mvc.service.model.AppUserPrincipal;
import com.gmail.artemkrotenok.mvc.service.model.UserDTO;
import com.gmail.artemkrotenok.mvc.service.util.UserUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.artemkrotenok.mvc.service.constants.PageConstants.ITEMS_BY_PAGE;

@Service
public class UserServiceImpl implements UserService {

    public static final long ID_SUPER_ADMINISTRATOR = 1;
    public static final long ID_IF_NOT_SELECTED_DELETE_USER = 0;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        userDTO.setPassword(UserUtil.getNewPassword());
        UserUtil.sendNewPasswordToEmail(userDTO.getPassword(), userDTO.getEmail());
        User user = UserUtil.getObjectFromDTO(userDTO);
        userRepository.persist(user);
    }

    @Override
    @Transactional
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = getDTOFromObject(user);
        }
        return userDTO;
    }

    @Override
    public AppUserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (AppUserPrincipal) authentication.getPrincipal();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Long getCountUsers() {
        return userRepository.getCount();
    }

    @Override
    @Transactional
    public List<UserDTO> getItemsByPage(int page) {
        int startPosition = ((page - 1) * ITEMS_BY_PAGE + 1) - 1;
        List<User> items = userRepository.getItemsByPage(startPosition, ITEMS_BY_PAGE);
        return convertItemsToItemsDTO(items);
    }

    @Override
    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        return getDTOFromObject(user);
    }

    @Override
    @Transactional
    public void changeUserRole(UserDTO userDTO) {
        Long id = userDTO.getId();
        if ((id != ID_IF_NOT_SELECTED_DELETE_USER) && (id != ID_SUPER_ADMINISTRATOR)) //Ban on removing super administrator
        {
            User user = userRepository.findById(id);
            user.setRole(userDTO.getRole());
        }
    }

    @Override
    @Transactional
    public List<UserDTO> getItemsByPageSorted(int page) {
        int startPosition = ((page - 1) * ITEMS_BY_PAGE + 1) - 1;
        List<User> items = userRepository.getItemsByPageSorted(startPosition, ITEMS_BY_PAGE);
        return convertItemsToItemsDTO(items);
    }

    @Override
    @Transactional
    public void deleteUsersByIds(Long[] userIds) {
        for (Long id : userIds) {
            if ((id != 0) && (id != 1)) //Ban on removing super administrator
            {
                User user = userRepository.findById(id);
                userRepository.remove(user);
            }
        }
    }

    @Override
    @Transactional
    public void changeUserPassword(Long id) {
        User user = userRepository.findById(id);
        user.setPassword(BCrypt.hashpw(UserUtil.getNewPassword(), BCrypt.gensalt()));
        UserUtil.sendNewPasswordToEmail(user.getPassword(), user.getEmail());
    }

    private List<UserDTO> convertItemsToItemsDTO(List<User> items) {
        return items.stream()
                .map(this::getDTOFromObject)
                .collect(Collectors.toList());
    }

    private UserDTO getDTOFromObject(User user) {
        return UserUtil.getDTOFromObject(user);
    }

}
