package com.istrate.fleetmanager.repository;

import com.istrate.fleetmanager.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
}
