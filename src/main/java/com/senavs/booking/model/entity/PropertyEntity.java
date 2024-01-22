package com.senavs.booking.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLACE")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "property_id_seq")
    private Long id;
    private String address;
    @ManyToOne(cascade = ALL, targetEntity = PersonEntity.class)
    @JoinColumn(name = "OWNER_TAX_ID")
    private PersonEntity owner;
}
