package com.henrique.bookshelf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henrique.bookshelf.model.Category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void categoryTest() throws Exception {
        String name = "Ficção";

        Category category = new Category(name);

        try {
            mockMvc.perform(post("/api/v1/category")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isCreated());

            assertAll("Category Registry",
                    () -> assertEquals(name, category.getName()),
                    () -> assertNotNull(category.getId()));

            mockMvc.perform(get("/api/v1/category/1"))
                .andDo(print())
                .andExpect(status().isOk());

            mockMvc.perform(get("/api/v1/category"))
                .andDo(print())
                .andExpect(status().isOk());

            mockMvc.perform(get("/api/v1/category"))
                .andDo(print())
                .andExpect(status().isOk());
            
            mockMvc.perform(delete("/api/v1/category/" + 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}