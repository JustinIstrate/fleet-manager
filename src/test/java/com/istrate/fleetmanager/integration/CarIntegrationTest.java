package com.istrate.fleetmanager.integration;

import com.istrate.fleetmanager.model.Car;
import com.istrate.fleetmanager.repository.CarRepository;
import com.istrate.fleetmanager.service.CarService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CarIntegrationTest {

    @Autowired
    private CarService carService;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    public void testSoftDelete_CarRemainsInButHidden(){

        Car car = new Car();
        car.setMake("TestMake");
        car.setModel("TestModel");
        car.setYear(2018);
        car.setLicensePlate("TEST B1");
        car.setMileage(100.0);

        Car savedCar= carService.addCar(car);
        long carId = savedCar.getId();

        assertNotNull(carId);

        carService.deleteCar(carId);

        entityManager.flush();
        entityManager.clear();

        Optional<Car> deletedCar= carRepository.findById(carId);
        assertTrue(deletedCar.isEmpty(),"The car should not be visible in the repo");

        Query nativeQuery = entityManager.createNativeQuery("SELECT count(*) FROM cars WHERE id=? AND deleted=true");
        nativeQuery.setParameter(1, carId);

        Long count = ((Number)nativeQuery.getSingleResult()).longValue();
        assertEquals(1,count,"The car should exist in the database and have delete=1");
    }
}
