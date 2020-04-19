package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.repository.UserRepository;
import com.gmail.artemkrotenok.repository.model.RoleEnum;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.service.impl.UserServiceImpl;
import com.gmail.artemkrotenok.service.model.UserDTO;
import com.gmail.artemkrotenok.service.util.UserUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.gmail.artemkrotenok.repository.model.RoleEnum.ADMINISTRATOR;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final long TEST_USER_ID = 2L;
    public static final String TEST_USER_SURNAME = "TestSurname";
    public static final String TEST_USER_NAME = "TestName";
    public static final String TEST_USER_MIDDLE_NAME = "TestMiddleName";
    public static final String TEST_USER_EMAIL = "test@test.com";
    public static final RoleEnum TEST_USER_ROLE = ADMINISTRATOR;
    public static final String TEST_PASSWORD = "testPassword";
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PasswordService passwordService;
    @Mock
    private UserUtil userUtil;

    private UserService userService;

    @BeforeEach
    public void setup() {
        this.userService = new UserServiceImpl(userRepository, passwordEncoder, passwordService, userUtil);
    }

    @Test
    @Disabled
    public void callGetUserByEmail_returnUserDTO() {
        User user = new User();
        when(userRepository.getUserByEmail(TEST_USER_EMAIL)).thenReturn(user);
        UserDTO userDTO = userService.getUserByEmail(TEST_USER_EMAIL);
        verify(userRepository, times(1)).getUserByEmail(TEST_USER_EMAIL);
        Assertions.assertThat(userDTO).isNotNull();
    }

    @Test
    @Disabled
    public void callAddUser_returnUserDTO() {
        UserDTO userDTO = getValidUserDTO();
        doNothing().when(userRepository).persist(any(User.class));
        UserDTO addedUserDTO = userService.add(userDTO);
        Assertions.assertThat(addedUserDTO).isNotNull();
        Assertions.assertThat(addedUserDTO.getEmail()).isEqualTo(userDTO.getEmail());
        verify(userRepository, times(1)).persist(any(User.class));
    }

    private UserDTO getValidUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(TEST_USER_ID);
        userDTO.setSurname(TEST_USER_SURNAME);
        userDTO.setName(TEST_USER_NAME);
        userDTO.setMiddleName(TEST_USER_MIDDLE_NAME);
        userDTO.setPassword(TEST_PASSWORD);
        userDTO.setEmail(TEST_USER_EMAIL);
        userDTO.setRole(TEST_USER_ROLE);
        return userDTO;
    }
}
