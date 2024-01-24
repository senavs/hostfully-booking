package com.senavs.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senavs.booking.model.entity.ReservationEntity;
import com.senavs.booking.model.request.RegisterReservationRequest;
import com.senavs.booking.repository.PersonRepository;
import com.senavs.booking.repository.PropertyRepository;
import com.senavs.booking.repository.ReservationRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ReservationControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final PersonRepository personRepository;
    private final PropertyRepository propertyRepository;
    private final ReservationRepository reservationRepository;

    @Test
    @SneakyThrows
    public void TEST_listAllReservationsForAProperty_THEN_return200WithAllReservations() {
        final ReservationEntity reservation = setupReservationDependencies();
        reservationRepository.save(reservation);
        final String requestBody = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("/reservation/%s", reservation.getProperty().getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").isNotEmpty()
        );
    }

    @Test
    @SneakyThrows
    public void TEST_listAllReservationsForAProperty_WHEN_allReservationDeleted_THEN_return200WithEmptyList() {
        final ReservationEntity reservation = setupReservationDependencies();
        reservation.setIsDeleted(true);
        reservationRepository.save(reservation);
        final String requestBody = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(
                MockMvcRequestBuilders.get(String.format("/reservation/%s", reservation.getProperty().getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").isEmpty()
        );
    }

    @Test
    @SneakyThrows
    public void TEST_deleteProperty_THEN_return202AndSetIsDeletedAsTrue() {
        final ReservationEntity reservation = setupReservationDependencies();
        reservationRepository.save(reservation);
        final String requestBody = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(
                MockMvcRequestBuilders.delete(String.format("/reservation/%s", reservation.getProperty().getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isAccepted()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("reservation was deleted successfully")
        );

        final ReservationEntity updatedReservation = reservationRepository.findById(reservation.getId()).get();
        assertTrue(updatedReservation.getIsDeleted());
    }

    @Test
    @SneakyThrows
    public void TEST_deleteProperty_WHEN_hardDeleteParamIsPassed_THEN_return202AndDeleteReservationFromDatabase() {
        final ReservationEntity reservation = setupReservationDependencies();
        reservationRepository.save(reservation);
        final String requestBody = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(
                MockMvcRequestBuilders.delete(String.format("/reservation/%s?hardDelete=true", reservation.getProperty().getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isAccepted()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("reservation was deleted successfully")
        );

        final Optional<ReservationEntity> updatedReservationOptional = reservationRepository.findById(reservation.getId());
        assertTrue(updatedReservationOptional.isEmpty());
    }

    @Test
    @SneakyThrows
    public void TEST_rebookProperty_THEN_return202AndReEnableReservation() {
        final ReservationEntity reservation = setupReservationDependencies();
        reservation.setIsDeleted(true);
        final Long reservationId = reservationRepository.save(reservation).getId();
        final String requestBody = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/reservation/%s/rebook", reservationId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isAccepted()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("reservation was rebook successfully")
        );

        final ReservationEntity updatedReservation = reservationRepository.findById(reservation.getId()).get();
        assertFalse(updatedReservation.getIsDeleted());
    }

    @Test
    @SneakyThrows
    public void TEST_rebookProperty_WHEN_thereIsAReservationInTheSameDataRange_THEN_return400() {
        final ReservationEntity reservationA = setupReservationDependencies();
        final ReservationEntity reservationB = setupReservationDependencies();
        reservationRepository.save(reservationA);
        reservationB.setIsDeleted(true);
        reservationRepository.save(reservationB);

        final Long reservationBId = reservationRepository.save(reservationB).getId();
        final String requestBody = objectMapper.writeValueAsString(reservationB);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/reservation/%s/rebook", reservationBId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Invalid Request")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0]").value("this property is already booked in this period")
        );

        final ReservationEntity updatedReservation = reservationRepository.findById(reservationBId).get();
        assertTrue(updatedReservation.getIsDeleted());
    }

    @Test
    @SneakyThrows
    public void TEST_bookProperty_THEN_return201AndReservationDetails() {
        final ReservationEntity reservation = setupReservationDependencies();
        final RegisterReservationRequest request = TestDataUtil.createTestRequestFromReservationEntity(reservation);
        final String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isBlockedByOwner").value(reservation.getIsBlockedByOwner())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.checkIn").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.checkOut").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isDeleted").value(false)
        );
    }

    @Test
    @SneakyThrows
    public void TEST_bookProperty_WHEN_thereIsAReservationInTheSameDateRangeAlready_THEN_return400() {
        final ReservationEntity reservation = setupReservationDependencies();
        final RegisterReservationRequest request = TestDataUtil.createTestRequestFromReservationEntity(reservation);
        final String requestBody = objectMapper.writeValueAsString(request);
        reservationRepository.save(reservation);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Invalid Request")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0]").value("this property is already booked in this period")
        );
    }


    @Test
    @SneakyThrows
    public void TEST_bookProperty_WHEN_thereIsBlockedByThePropertyOwner_THEN_return400() {
        final ReservationEntity reservation = setupReservationDependencies();
        reservation.setIsBlockedByOwner(true);
        final RegisterReservationRequest request = TestDataUtil.createTestRequestFromReservationEntity(reservation);
        final String requestBody = objectMapper.writeValueAsString(request);
        reservationRepository.save(reservation);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Invalid Request")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0]").value("property owner blocked new reservations in this date range")
        );
    }

    private ReservationEntity setupReservationDependencies() {
        final ReservationEntity reservation = TestDataUtil.createTestReservationEntity();
        personRepository.save(reservation.getProperty().getOwner());
        propertyRepository.save(reservation.getProperty());
        return reservation;
    }
}