package com.senavs.booking.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PERSON")
public class PersonEntity {

    @Id
    private String taxId;
    private String name;
    private Integer age;
    @JsonBackReference
    @ManyToMany(mappedBy = "guests")
    private List<ReservationEntity> reservations;

}
