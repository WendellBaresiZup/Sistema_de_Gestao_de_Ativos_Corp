package com.zup.gestaodeativos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "ativos")
public class Active {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String type;
    private String model;
    @Column(nullable = false, unique = true)
    private String serialNumber;
    @Column(nullable = false)
    private LocalDate acquisitionDate;
    @Column(nullable = false)
    private LocalDate warrantyExpires;

}