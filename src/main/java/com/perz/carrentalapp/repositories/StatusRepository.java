package com.perz.carrentalapp.repositories;

import com.perz.carrentalapp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByName(String name);

}
