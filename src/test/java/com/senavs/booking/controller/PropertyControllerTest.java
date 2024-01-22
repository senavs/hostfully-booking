package com.senavs.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senavs.booking.model.entity.PropertyEntity;
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
class PropertyControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final PropertyRepository propertyRepository;

    @Test
    @SneakyThrows
    public void testListAllOwnerPropertiesSuccessfullyReturnsHttp200AndListOfAllProperties() {
        final PropertyEntity propertyA = TestDataUtil.createTestPropertyEntity();
        final PropertyEntity propertyB = TestDataUtil.createTestPropertyEntity();
        propertyRepository.save(propertyA);
        propertyRepository.save(propertyB);
        final String requestBody = objectMapper.writeValueAsString(propertyA);

        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("/property/%s", propertyA.getOwner().getTaxId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").isArray()
        );
    }

    @Test
    @SneakyThrows
    public void testCreatePropertySuccessfullyReturnsHttp201CreatedAndPropertyAsJson() {
        final PropertyEntity testPropertyEntity = TestDataUtil.createTestPropertyEntity();
        final String requestBody = objectMapper.writeValueAsString(testPropertyEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.address").value(testPropertyEntity.getAddress())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.owner.taxId").value(testPropertyEntity.getOwner().getTaxId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.owner.name").value(testPropertyEntity.getOwner().getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.owner.age").value(testPropertyEntity.getOwner().getAge())
        );
    }

    @Test
    @SneakyThrows
    public void testCreatePropertyWithInvalidRequestBodyReturningBadRequest() {
        final PropertyEntity testPropertyEntity = TestDataUtil.createTestPropertyEntityWithoutOwner();
        final String requestBody = objectMapper.writeValueAsString(testPropertyEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Request validation error")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty()
        );
    }

}