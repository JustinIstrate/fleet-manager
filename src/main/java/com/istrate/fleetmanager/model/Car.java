package com.istrate.fleetmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql="UPDATE cars SET deleted=true WHERE id=?")
@SQLRestriction("deleted=false")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceLog> maintenanceLogs;
    @NotBlank(message= "Make must be specified")
    private String make;
    @NotBlank(message="Model must be specified")
    private String model;
    @Column(name = "production_year")
    @Min(value=1950, message = "The car must be produced after 1950")
    @Max(value=2026,message = "The year must exist")
    private int year;
    @NotBlank(message = "License plate must be specified")
    @Size(min=6,max=10,message = "License plate must be between 6 and 10 characters")
    private String licensePlate;
    @Min(value=0,message="Mileage cannot be negative")
    private double mileage;

    @Column(nullable = false)
    private boolean deleted=false;

    private LocalDate itpExpiryDate;
}