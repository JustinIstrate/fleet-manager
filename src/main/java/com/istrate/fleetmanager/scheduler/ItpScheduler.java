package com.istrate.fleetmanager.scheduler;

import com.istrate.fleetmanager.model.Car;
import com.istrate.fleetmanager.repository.CarRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ItpScheduler {
    private final CarRepository carRepository;
    public ItpScheduler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Scheduled(fixedRate = 10000)
    public void checkExpiringItps(){
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow=today.plusDays(7);

        System.out.println("Checking expiring ITPs between "+today + " and "+sevenDaysFromNow);
        List<Car>  expiringCars=carRepository.findByItpExpiryDateBetween(today,sevenDaysFromNow);

        if(expiringCars.isEmpty()){
            System.out.println("No cars found with expiring ITP");
        } else{
            System.out.println("We found "+expiringCars.size()+" cars with expiring ITP");
            for(Car car:expiringCars){
                System.out.println("  -->"+car.getMake()+" "+car.getModel()+" ("+car.getLicensePlate()+" ) expires on: "+car.getItpExpiryDate());
            }
        }
    }
}
