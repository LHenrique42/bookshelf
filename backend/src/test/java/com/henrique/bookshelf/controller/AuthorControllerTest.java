package com.henrique.bookshelf.controller;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henrique.bookshelf.model.Author;

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
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registryAuthor() throws Exception {
        String name = "Luis Henrique";
        LocalDate birthdayDate = LocalDate.parse("1997-09-01");

        Author author = new Author(name, birthdayDate);

        try {
            mockMvc.perform(post("/api/author")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated());

            assertAll("Author Registry",
                    () -> assertEquals(name, author.getName()),
                    () -> assertEquals(birthdayDate, author.getDateOfBirthday()),
                    () -> assertNotNull(author.getId()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void findAuthor() throws Exception {
        String name = "Luis Henrique";
        LocalDate birthdayDate = LocalDate.parse("1997-09-01");

        Author author = new Author(name, birthdayDate);

        try {
            mockMvc.perform(post("/api/author")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andDo(print())
                .andExpect(status().isCreated());

            assertAll("Author Registry",
                    () -> assertEquals(name, author.getName()),
                    () -> assertEquals(birthdayDate, author.getDateOfBirthday()),
                    () -> assertNotNull(author.getId()));

            mockMvc.perform(get("/api/author/" + author.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"xxx\",\"dateOfBirthday\":\"xxx\", \"Book\":\"xxx\"}"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}