package com.perz.carrentalapp.repositories;

import com.perz.carrentalapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM  Order o INNER JOIN o.status s " +
            "WHERE o.car.id = :carId AND (o.end >= :start AND o.start <= :end) " +
            "AND (s.name <> 'STATUS_COMPLETE' AND s.name <> 'STATUS_CANCELLED') ")
    List<Order> findActiveOrdersByCarAndDate(@Param("carId")Long carId, @Param("start")LocalDate start,
                                             @Param("end") LocalDate end);
}
