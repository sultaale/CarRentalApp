package com.perz.carrentalapp.auth.repositories;


import com.perz.carrentalapp.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);
}
