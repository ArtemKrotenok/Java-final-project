package com.gmail.artemkrotenok.service.impl;

import com.gmail.artemkrotenok.repository.UserRepository;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.service.PasswordService;
import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.constants.PageConstants;
import com.gmail.artemkrotenok.service.model.UpdateUserDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public static final long ID_SUPER_ADMINISTRATOR = 1;
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordService passwordService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordService = passwordService;
    }

    @Override
    @Transactional
    public UserDTO add(UserDTO userDTO) {
        User user = getObjectFromDTO(userDTO);
        String newUserPassword = passwordService.getNewPassword();
        String encodedPassword = passwordEncoder.encode(newUserPassword);
        user.setPassword(encodedPassword);
        userRepository.persist(user);
        Boolean passwordSend = passwordService.sendPasswordToEmail(newUserPassword, user.getEmail());
        if (!passwordSend) {
            logger.error("Failed to send password to e-mail: '" + user.getEmail() + "'");
        }
        return getDTOFromObject(user);
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
    @Transactional
    public Long getCountUsers() {
        return userRepository.getCount();
    }

    @Override
    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        return getDTOFromObject(user);
    }

    @Override
    @Transactional
    public UserDTO changeUserRole(UpdateUserDTO updateUserDTO) {
        Long id = updateUserDTO.getId();
        User user = userRepository.findById(id);
        if (id != ID_SUPER_ADMINISTRATOR) //Ban on removing super administrator
        {
            user.setRole(updateUserDTO.getRole());
        }
        return getDTOFromObject(user);
    }

    @Override
    @Transactional
    public List<UserDTO> getItemsByPageSorted(int page) {
        int startPosition = ((page - 1) * PageConstants.ITEMS_BY_PAGE + 1) - 1;
        List<User> items = userRepository.getItemsByPageSorted(startPosition, PageConstants.ITEMS_BY_PAGE);
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
        String newUserPassword = passwordService.getNewPassword();
        String encodedPassword = passwordEncoder.encode(newUserPassword);
        user.setPassword(encodedPassword);
        Boolean passwordSend = passwordService.sendPasswordToEmail(newUserPassword, user.getEmail());
        if (!passwordSend) {
            logger.error("Failed to send password to e-mail: '" + user.getEmail() + "'");
        }
    }

    private List<UserDTO> convertItemsToItemsDTO(List<User> items) {
        return items.stream()
                .map(this::getDTOFromObject)
                .collect(Collectors.toList());
    }

    private UserDTO getDTOFromObject(User user) {
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

    private User getObjectFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }
}
