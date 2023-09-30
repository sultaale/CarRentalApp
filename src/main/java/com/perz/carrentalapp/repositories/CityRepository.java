package com.perz.carrentalapp.repositories;

import com.perz.carrentalapp.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);
}
