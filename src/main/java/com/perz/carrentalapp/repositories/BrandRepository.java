package com.perz.carrentalapp.repositories;

import com.perz.carrentalapp.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);

    Optional<Brand> findByModel(String model);
}
