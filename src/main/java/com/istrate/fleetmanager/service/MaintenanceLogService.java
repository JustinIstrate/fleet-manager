package com.istrate.fleetmanager.service;

import com.istrate.fleetmanager.model.Car;
import com.istrate.fleetmanager.model.MaintenanceLog;
import com.istrate.fleetmanager.repository.CarRepository;
import com.istrate.fleetmanager.repository.MaintenanceLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceLogService {

    private final MaintenanceLogRepository maintenanceLogRepository;
    private final CarRepository carRepository;

    public MaintenanceLogService(MaintenanceLogRepository maintenanceLogRepository, CarRepository carRepository) {
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.carRepository = carRepository;
    }

    public MaintenanceLog addMaintenanceLog(Long carId,MaintenanceLog log){
        Car car=carRepository.findById(carId).orElseThrow(()-> new RuntimeException("Car not found with id: "+carId));
        log.setCar(car);
        return maintenanceLogRepository.save(log);
    }

    public List<MaintenanceLog> getLogsByCarId(Long carId){
        return maintenanceLogRepository.findByCarId(carId);
    }
    public Double calculateTotalCost(Long carId){
        Double total = maintenanceLogRepository.getTotalCostByCarId(carId);
        return (total != null) ? total : 0.0;
    }
}
