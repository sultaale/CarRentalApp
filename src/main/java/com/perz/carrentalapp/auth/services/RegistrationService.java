package com.perz.carrentalapp.auth.services;


import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.repositories.UserRepository;
import com.perz.carrentalapp.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_USER").get());
        user.setDisabled(false);
        usersRepository.save(user);
    }
}
