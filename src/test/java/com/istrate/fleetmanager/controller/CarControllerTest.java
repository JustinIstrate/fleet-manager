package com.istrate.fleetmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.istrate.fleetmanager.model.Car;
import com.istrate.fleetmanager.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void whenPostInvalidCar_thenReturns400() throws Exception {
        Car invalidCar = new Car();
        invalidCar.setMake("");
        invalidCar.setModel("Future");
        invalidCar.setYear(3000);
        invalidCar.setLicensePlate("A");
        invalidCar.setMileage(-100.0);
        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCar)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.make").exists())
                .andExpect(jsonPath("$.year").value("The year must exist"));
    }

    @Test
    public void whenPostValidCar_thenReturns200() throws Exception {
        Car validCar = new Car();
        validCar.setMake("Dacia");
        validCar.setModel("Logan");
        validCar.setYear(2022);
        validCar.setLicensePlate("IS 10 XYY");
        validCar.setMileage(15000.0);

        when(carService.addCar(any(Car.class))).thenReturn(validCar);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Dacia"));
    }
}