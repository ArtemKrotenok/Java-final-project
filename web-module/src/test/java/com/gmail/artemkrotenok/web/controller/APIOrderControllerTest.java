package com.gmail.artemkrotenok.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.artemkrotenok.service.OrderService;
import com.gmail.artemkrotenok.service.model.OrderDTO;
import com.gmail.artemkrotenok.web.controller.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = APIOrderController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class
)
@Import(TestConfig.class)
@WithMockUser(roles = "SECURE_API_USER")
class APIOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void deleteOrder() throws Exception {

        when(orderService.findById(eq(10L)))
                .thenReturn(new OrderDTO());
        mockMvc.perform(
                get("/api/orders/11")
        ).andExpect(status().isOk());
    }

}
