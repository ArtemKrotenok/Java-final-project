package com.gmail.artemkrotenok.web.controller;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.artemkrotenok.service.ItemService;
import com.gmail.artemkrotenok.service.model.ItemDTO;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = APIItemController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class
)
@Import(TestConfig.class)
@WithMockUser(roles = "SECURE_API_USER")
class APIItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Test
    void addItem() throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("testName");
        itemDTO.setPrice(BigDecimal.valueOf(100));
        itemDTO.setDescription("testDescription");
        String content = objectMapper.writeValueAsString(itemDTO);
        mockMvc.perform(
                post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
        ).andExpect(status().isCreated());
    }

    @Test
    void deleteItem() throws Exception {

        when(itemService.deleteById(eq(10L)))
                .thenReturn(true);
        mockMvc.perform(
                delete("/api/items/11")
        ).andExpect(status().isOk());
    }

}
