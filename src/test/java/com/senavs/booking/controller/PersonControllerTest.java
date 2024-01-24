package com.senavs.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.repository.PersonRepository;
import com.senavs.booking.repository.PropertyRepository;
import com.senavs.booking.utils.TestDataUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PersonControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final PersonRepository personRepository;

    @Test
    @SneakyThrows
    public void TEST_registerNewPerson_THEN_return201AndPersonAsJson() {
        final PersonEntity person = TestDataUtil.createTestPersonEntity();
        final String requestBody = objectMapper.writeValueAsString(person);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taxId").value(person.getTaxId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(person.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(person.getAge())
        );
    }

}