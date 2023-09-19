package com.perz.carrentalapp.service;


import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository usersRepository;

    public Optional<User> existingEmail(User user) {
        return usersRepository.findByEmail(user.getEmail());
    }

    public Optional<User> existingPhone(User user) {
        return usersRepository.findByPhone(user.getPhone());
    }
}
