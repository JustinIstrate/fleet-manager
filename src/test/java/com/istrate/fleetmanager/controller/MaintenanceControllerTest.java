package com.istrate.fleetmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.istrate.fleetmanager.model.MaintenanceLog;
import com.istrate.fleetmanager.service.MaintenanceLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaintenanceLogController.class)
public class MaintenanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MaintenanceLogService maintenanceLogService;

   private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void shouldAddMaintenanceLog() throws Exception {
        Long carId = 1L;
        MaintenanceLog log = new MaintenanceLog();
        log.setDescription("Oil change");
        log.setCost(500.0);
        log.setDate(LocalDate.now());

        when(maintenanceLogService.addMaintenanceLog(eq(carId), any(MaintenanceLog.class))).thenReturn(log);

        mockMvc.perform(post("/api/maintenance/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(log)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Oil change"))
                .andExpect(jsonPath("$.cost").value(500.0));
    }

    @Test
    public void shouldGetLogsByCarId() throws Exception {
        Long carId = 1L;
        MaintenanceLog log1 = new MaintenanceLog();
        log1.setDescription("Brake pads");
        log1.setCost(200.0);

        MaintenanceLog log2 = new MaintenanceLog();
        log2.setDescription("Wipers");
        log2.setCost(50.0);

        List<MaintenanceLog> logs = Arrays.asList(log1, log2);

        when(maintenanceLogService.getLogsByCarId(carId)).thenReturn(logs);

        mockMvc.perform(get("/api/maintenance/{carId}", carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Brake pads"))
                .andExpect(jsonPath("$[1].description").value("Wipers"));
    }

    @Test
    public void shouldGetTotalCost() throws Exception {
        Long carId = 1L;
        Double expectedCost = 1250.50;

        when(maintenanceLogService.calculateTotalCost(carId)).thenReturn(expectedCost);

        mockMvc.perform(get("/api/maintenance/total/{carId}", carId))
                .andExpect(status().isOk())
                .andExpect(content().string("1250.5"));
    }
}