package com.borani.spring.boot.scheduler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dynamic_property")
@Getter
@Setter
public class DynamicPropertyEntity {

    @Id
    @Column(name = "prop_id", nullable = false, updatable = false)
    private int propId;

    @Column(name = "prop_key", nullable = false, updatable = false)
    private String propKey;

    @Column(name = "prop_value", nullable = false, updatable = true)
    private String propValue;

    @Column(name = "prop_identifier", nullable = false, updatable = true)
    private String propIdentifier;
}
