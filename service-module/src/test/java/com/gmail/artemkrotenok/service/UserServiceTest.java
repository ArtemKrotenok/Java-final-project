package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.repository.UserRepository;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.repository.model.UserInformation;
import com.gmail.artemkrotenok.repository.model.UserRoleEnum;
import com.gmail.artemkrotenok.service.impl.UserServiceImpl;
import com.gmail.artemkrotenok.service.model.UpdateUserDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import com.gmail.artemkrotenok.service.util.UserConverterUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.gmail.artemkrotenok.repository.model.UserRoleEnum.ADMINISTRATOR;
import static com.gmail.artemkrotenok.repository.model.UserRoleEnum.SALE_USER;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final long TEST_USER_ID = 2L;
    public static final String TEST_USER_SURNAME = "TestSurname";
    public static final String TEST_USER_NAME = "TestName";
    public static final String TEST_USER_MIDDLE_NAME = "TestMiddleName";
    public static final String TEST_USER_EMAIL = "test@test.com";
    public static final UserRoleEnum TEST_USER_ROLE = ADMINISTRATOR;
    public static final UserRoleEnum TEST_USER_ROLE_ANY = SALE_USER;
    public static final String TEST_PASSWORD = "testPassword";
    private static final String TEST_USER_ADDRESS = "Belarus, Minsk, Administratorov str., 123-1/3";
    private static final String TEST_USER_PHONE = "+375291111111";
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PasswordService passwordService;

    private UserService userService;

    @BeforeEach
    public void setup() {
        this.userService = new UserServiceImpl(userRepository, passwordEncoder, passwordService);
    }

    @Test
    public void getUserByEmail_returnUserDTO() {
        User user = getEmptyUser();
        when(userRepository.getUserByEmail(TEST_USER_EMAIL)).thenReturn(user);
        UserDTO userDTO = userService.getUserByEmail(TEST_USER_EMAIL);
        verify(userRepository, times(1)).getUserByEmail(TEST_USER_EMAIL);
        Assertions.assertThat(userDTO).isNotNull();
    }

    @Test
    public void add_returnUserDTO() {
        UserDTO userDTO = getValidUserDTO();
        doNothing().when(userRepository).persist(any(User.class));
        UserDTO addedUserDTO = userService.add(userDTO);
        Assertions.assertThat(addedUserDTO).isNotNull();
        Assertions.assertThat(addedUserDTO.getEmail()).isEqualTo(userDTO.getEmail());
        verify(userRepository, times(1)).persist(any(User.class));
    }

    @Test
    public void getUserById_returnUserDTO() {
        User user = getEmptyUser();
        when(userRepository.findById(TEST_USER_ID)).thenReturn(user);
        UserDTO userDTO = userService.getUserById(TEST_USER_ID);
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        Assertions.assertThat(userDTO).isNotNull();
    }

    @Test
    public void changeUserRole_returnChangeUserDTO() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setId(TEST_USER_ID);
        updateUserDTO.setRole(TEST_USER_ROLE_ANY);
        User user = UserConverterUtil.getObjectFromDTO(getValidUserDTO());
        when(userRepository.findById(TEST_USER_ID)).thenReturn(user);
        UserDTO userDTO = userService.changeUserRole(updateUserDTO);
        Assertions.assertThat(userDTO.getRole()).isEqualTo(TEST_USER_ROLE_ANY);
    }

    @Test
    public void getUserById_returnNull() {
        when(userRepository.findById(TEST_USER_ID)).thenReturn(null);
        UserDTO userDTO = userService.getUserById(TEST_USER_ID);
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        Assertions.assertThat(userDTO).isNull();
    }

    private User getEmptyUser() {
        User user = new User();
        UserInformation userInformation = new UserInformation();
        user.setUserInformation(userInformation);
        return user;
    }

    private UserDTO getValidUserDTO() {
        return UserConverterUtil.getDTOFromObject(getValidUser());
    }

    private User getValidUser() {
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setSurname(TEST_USER_SURNAME);
        user.setName(TEST_USER_NAME);
        user.setMiddleName(TEST_USER_MIDDLE_NAME);
        user.setPassword(TEST_PASSWORD);
        user.setEmail(TEST_USER_EMAIL);
        user.setRole(TEST_USER_ROLE);
        UserInformation userInformation = new UserInformation();
        userInformation.setPhone(TEST_USER_PHONE);
        userInformation.setAddress(TEST_USER_ADDRESS);
        user.setUserInformation(userInformation);
        return user;
    }

}
