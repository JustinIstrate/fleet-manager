package com.istrate.fleetmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarExpenseDTO {
    private Long carId;
    private String make;
    private String model;
    private String licensePlate;
    private Double totalCost;
}
