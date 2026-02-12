package com.istrate.fleetmanager.repository;

import com.istrate.fleetmanager.dto.CarExpenseDTO;
import com.istrate.fleetmanager.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
    @Query("SELECT new com.istrate.fleetmanager.dto.CarExpenseDTO(c.id, c.make, c.model, c.licensePlate, SUM(m.cost)) " +
            "FROM Car c " +
            "JOIN c.maintenanceLogs m " +
            "GROUP BY c.id " +
            "ORDER BY SUM(m.cost) DESC")
    List<CarExpenseDTO> findTopExpensiveCars();
    List<Car> findByItpExpiryDateBetween(LocalDate start, LocalDate end);
}
