package com.perz.carrentalapp.repositories;

import com.perz.carrentalapp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.availability AND c.id NOT IN " +
            "(SELECT o.car.id FROM Order o INNER JOIN o.status s " +
            "WHERE (o.end >= :start AND o.start <= :end) " +
            "AND (s.name <> 'STATUS_COMPLETE'AND s.name <> 'STATUS_CANCELED'))")
    List<Car> findAvailableCarsForDate(@Param("start")LocalDate start,
                                       @Param("end") LocalDate end);

}
