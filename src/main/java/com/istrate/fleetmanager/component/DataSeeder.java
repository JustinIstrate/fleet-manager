package com.istrate.fleetmanager.component;

import net.datafaker.Faker;
import com.istrate.fleetmanager.model.Car;
import com.istrate.fleetmanager.model.MaintenanceLog;
import com.istrate.fleetmanager.repository.CarRepository;
import com.istrate.fleetmanager.repository.MaintenanceLogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CarRepository carRepository;
    private final MaintenanceLogRepository maintenanceLogRepository;
    private final Faker faker;

    public DataSeeder(CarRepository carRepository, MaintenanceLogRepository maintenanceLogRepository) {
        this.carRepository = carRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        if(carRepository.count()==0){
            System.out.println("🌱 Starting Data Seeding...");

            for(int i=0;i<50;i++){
                Car car = new Car();
                String[] county = {"AB", "AR", "AG", "BC", "BH", "BN", "BT", "BR", "BV", "B", "BZ", "CS", "CL", "CJ", "CT", "CV", "DB", "DJ", "GL", "GR", "GJ", "HR", "HD", "IL", "IS", "IF", "MM", "MH", "MS", "NT", "OT", "PH", "SM", "SJ", "SB", "SV", "TR", "TM", "TL", "VS", "VL", "VN"
                };
                String randomCounty =county[new Random().nextInt(county.length)];
                String digits = county.equals("B") ? faker.numerify("###") : faker.numerify("##");
                String letters = faker.bothify("???").toUpperCase();
                String vehicleName=faker.vehicle().makeAndModel();
                String make = vehicleName.split(" ")[0];
                String model = vehicleName.substring(make.length()).trim();
                car.setMake(make);
                car.setModel(model);
                car.setYear(faker.number().numberBetween(2010,2025));
                car.setLicensePlate(faker.expression(randomCounty+" "+digits+" "+letters));
                car.setMileage(faker.number().randomDouble(2,10000,500000));
                car.setItpExpiryDate(LocalDate.now().plusDays(faker.random().nextInt(1,730)));
                carRepository.save(car);

                int logsCount = faker.number().numberBetween(0,6);
                for(int j=0;j<logsCount;j++){
                    MaintenanceLog log = new MaintenanceLog();
                    log.setDate(LocalDate.now().minusDays(faker.number().numberBetween(1,1000)));
                    log.setDescription("Service: "+faker.vehicle().carOptions());
                    log.setCost(faker.number().randomDouble(2,200,10000));
                    log.setCar(car);
                    maintenanceLogRepository.save(log);
                }
            }
            System.out.println("✅ Data Seeding Completed! 50 cars and logs generated.");
        }
    }
}
