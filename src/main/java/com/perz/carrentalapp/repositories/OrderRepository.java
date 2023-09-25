package com.perz.carrentalapp.repositories;

import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.model.Status;
import com.perz.carrentalapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatus(Status status);
    List<Order> findAllByUserId(Long userId);
    List<Order> findAllByCar(Car car);

}
