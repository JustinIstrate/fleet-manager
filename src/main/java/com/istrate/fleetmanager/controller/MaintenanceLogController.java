package com.istrate.fleetmanager.controller;

import com.istrate.fleetmanager.model.MaintenanceLog;
import com.istrate.fleetmanager.service.MaintenanceLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceLogController {

    private final MaintenanceLogService maintenanceLogService;

    public MaintenanceLogController(MaintenanceLogService maintenanceLogService) {
        this.maintenanceLogService = maintenanceLogService;
    }

    @PostMapping("/{carId}")
    public MaintenanceLog addLog(@PathVariable Long carId, @RequestBody MaintenanceLog log){
        return maintenanceLogService.addMaintenanceLog(carId, log);
    }

    @GetMapping("/{carId}")
    public List<MaintenanceLog> getLogs(@PathVariable Long carId){
        return maintenanceLogService.getLogsByCarId(carId);
    }

    @GetMapping("/total/{carId}")
    public Double getTotalCost(@PathVariable Long carId){
        return maintenanceLogService.calculateTotalCost(carId);
    }
}
