package com.istrate.fleetmanager.controller;

import com.istrate.fleetmanager.dto.CarExpenseDTO;
import com.istrate.fleetmanager.model.Car;
import com.istrate.fleetmanager.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
    @Operation(summary = "Find a car by ID", description = "Returns a single car if found, otherwise throws 404.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car found successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found in the database")
    })
    @GetMapping("/{id}")
    public Car  getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }
    @PostMapping
    public Car addCar(@Valid @RequestBody Car car) {
        return carService.addCar(car);
    }
    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        return carService.updateCar(id, updatedCar);
    }
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
    @GetMapping("/top-expensive")
    public List<CarExpenseDTO> getTopExpensiveCars() {
        return carService.getTopThreeExpensiveCars();
    }
}
