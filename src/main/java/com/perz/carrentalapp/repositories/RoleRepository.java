package com.perz.carrentalapp.repositories;


import com.perz.carrentalapp.auth.model.User;
import com.perz.carrentalapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);

}
