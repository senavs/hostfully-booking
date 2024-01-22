package com.senavs.booking.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RESERVATION")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "reservation_id_seq")
    private Long id;

    private Boolean blockedByOwner;

    private LocalDate checkIn;

    private LocalDate checkOut;

    @ManyToOne(cascade = ALL, targetEntity = PropertyEntity.class)
    @JoinColumn(name = "PROPERTY_ID")
    private PropertyEntity property;

    @JsonManagedReference
    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "GUEST_RESERVATIONS",
            joinColumns = {@JoinColumn(name = "RESERVATION_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "PERSON_TAX_ID", referencedColumnName = "taxId")}
    )
    private List<PersonEntity> guests;
}
