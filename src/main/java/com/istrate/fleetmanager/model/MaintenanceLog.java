package com.istrate.fleetmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="maintenance_logs")
public class MaintenanceLog {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private LocalDate date; //date of repair
    private String description;
    private double cost; //repair cost

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="car_id",nullable = false)
    @JsonIgnore
    private Car car;

}
