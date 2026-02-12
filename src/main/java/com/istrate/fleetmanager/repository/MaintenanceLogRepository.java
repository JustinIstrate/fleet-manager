package com.istrate.fleetmanager.repository;

import com.istrate.fleetmanager.model.MaintenanceLog;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog,Long> {
    List<MaintenanceLog> findByCarId(Long carId);

    @Query("SELECT SUM(m.cost) FROM MaintenanceLog m WHERE m.car.id= :carId")
    Double getTotalCostByCarId(@Param("carId") Long carId);
}
