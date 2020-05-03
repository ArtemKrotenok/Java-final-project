package com.gmail.artemkrotenok.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.artemkrotenok.repository.model.UserRoleEnum;
import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.model.UserDTO;
import com.gmail.artemkrotenok.web.controller.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = APIUserController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class
)
@Import(TestConfig.class)
@WithMockUser(roles = "SECURE_API_USER")
class APIUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void addUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("TESTName");
        userDTO.setSurname("TESTSurname");
        userDTO.setMiddleName("TESTMiddleName");
        userDTO.setEmail("BIGTEST@email.com");
        userDTO.setRole(UserRoleEnum.SALE_USER);
        String content = objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
        ).andExpect(status().isCreated());
    }
}
