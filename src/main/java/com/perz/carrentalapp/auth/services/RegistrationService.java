package com.perz.carrentalapp.auth.services;


import com.perz.carrentalapp.auth.model.User;
import com.perz.carrentalapp.auth.repositories.UsersRepository;
import com.perz.carrentalapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegistrationService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UsersRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void register(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_USER").get());
        user.setDisabled(false);
        usersRepository.save(user);
    }
}
