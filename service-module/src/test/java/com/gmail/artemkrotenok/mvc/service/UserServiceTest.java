package com.gmail.artemkrotenok.mvc.service;

import com.gmail.artemkrotenok.mvc.repository.UserRepository;
import com.gmail.artemkrotenok.mvc.repository.enums.RoleEnum;
import com.gmail.artemkrotenok.mvc.repository.impl.UserRepositoryImpl;
import com.gmail.artemkrotenok.mvc.repository.model.User;
import com.gmail.artemkrotenok.mvc.service.impl.UserServiceImpl;
import com.gmail.artemkrotenok.mvc.service.model.UserDTO;
import com.gmail.artemkrotenok.mvc.service.util.UserUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gmail.artemkrotenok.mvc.repository.enums.RoleEnum.ROLE_ADMINISTRATOR;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final long TEST_USER_ID = 2L;
    public static final String TEST_USER_SURNAME = "TestSurname";
    public static final String TEST_USER_NAME = "TestName";
    public static final String TEST_USER_MIDDLE_NAME = "TestMiddleName";
    public static final String TEST_USER_EMAIL = "test@test.com";
    public static final RoleEnum TEST_USER_ROLE = ROLE_ADMINISTRATOR;
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        this.userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void callGetUserByEmail_returnUserDTO() {
        User user = new User();
        when(userRepository.getUserByEmail(TEST_USER_EMAIL)).thenReturn(user);
        UserDTO UserDTO = userService.getUserByEmail(TEST_USER_EMAIL);
        verify(userRepository, times(1)).getUserByEmail(TEST_USER_EMAIL);
        Assertions.assertThat(UserDTO).isNotNull();
    }

    @Test
    public void callAddUser() {
        UserDTO userDTO = getValidUserDTO();
        User user = UserUtil.getObjectFromDTO(userDTO);
        userService.add(userDTO);
        verify(userRepository, times(1)).persist(user);
    }

    private UserDTO getValidUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(TEST_USER_ID);
        userDTO.setSurname(TEST_USER_SURNAME);
        userDTO.setName(TEST_USER_NAME);
        userDTO.setMiddleName(TEST_USER_MIDDLE_NAME);
        userDTO.setPassword(UserUtil.getNewPassword());
        userDTO.setEmail(TEST_USER_EMAIL);
        userDTO.setRole(TEST_USER_ROLE);
        return userDTO;
    }

}
