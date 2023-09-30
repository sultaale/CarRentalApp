package com.perz.carrentalapp.repositories;

import com.perz.carrentalapp.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, Long> {
}
