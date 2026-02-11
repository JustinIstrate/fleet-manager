package com.istrate.fleetmanager.service;
import com.istrate.fleetmanager.model.Car;
import com.istrate.fleetmanager.repository.CarRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    public Car addCar(Car car) {
        return carRepository.save(car);
    }
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
    public Car updateCar(Long id, Car updatedCar){
        return carRepository.findById(id)
                .map(car -> {
                    car.setMake(updatedCar.getMake());
                    car.setModel(updatedCar.getModel());
                    car.setYear(updatedCar.getYear());
                    car.setLicensePlate(updatedCar.getLicensePlate());
                    car.setMileage(updatedCar.getMileage());
                    return carRepository.save(car);
                })
                .orElseThrow(() -> new RuntimeException("Car not found with id " + id));
    }
}
