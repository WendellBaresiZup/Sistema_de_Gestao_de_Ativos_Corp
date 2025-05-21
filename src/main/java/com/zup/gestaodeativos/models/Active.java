package com.zup.gestaodeativos.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Builder.Default
    private boolean available = true;

    @OneToMany(mappedBy = "active", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssetAssignment> assignments = new ArrayList<>();
}