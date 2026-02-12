package com.istrate.fleetmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.istrate.fleetmanager.model.Car;
import com.istrate.fleetmanager.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc; //request robot

    @MockitoBean
    private CarService carService; //service simulator

    private ObjectMapper objectMapper= new ObjectMapper().registerModule(new JavaTimeModule()); // transform objects into json

    @Test
    public void whenPostInvalidCar_returns400() throws Exception {
        Car invalidCar = new Car();
        invalidCar.setMake("");
        invalidCar.setModel("Future");
        invalidCar.setYear(4000);
        invalidCar.setLicensePlate("AU");
        invalidCar.setMileage(-10000.0);

        mockMvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCar)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.make").exists())
                .andExpect(jsonPath("$.year").value("The year must exist"));
    }
    @Test
    public void whenPostValidCar_returns200() throws Exception {
        Car validCar = new Car();
        validCar.setMake("Ford");
        validCar.setModel("Focus");
        validCar.setYear(2020);
        validCar.setLicensePlate("IS 99 TST");
        validCar.setMileage(150000.0);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCar)))

                .andExpect(status().isOk());
    }
}
