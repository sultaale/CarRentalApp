package com.perz.carrentalapp.repositories;

import com.perz.carrentalapp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository extends JpaRepository<Car, Long> {
}
