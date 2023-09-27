package com.perz.carrentalapp.service;


import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.repositories.RoleRepository;
import com.perz.carrentalapp.repositories.UserRepository;
import com.perz.carrentalapp.util.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_USER").get());
        user.setDisabled(false);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MANAGER')|| hasRole('ROLE_USER')")
    public User getById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        checkIfUserIsNull(user);

        return user;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(null);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public void update(Long id, User user) {

        User userToBeUpdate = userRepository.findById(id).orElse(null);

        checkIfUserIsNull(userToBeUpdate);

            userToBeUpdate.setFirstname(user.getFirstname());
            userToBeUpdate.setLastname(user.getLastname());
            userToBeUpdate.setEmail(user.getEmail());
            userToBeUpdate.setPhone(user.getPhone());
            userToBeUpdate.setPassword(user.getPassword());

            userRepository.save(userToBeUpdate);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public void delete(Long id) {

        User userToBeDelete = userRepository.findById(id).orElse(null);

        checkIfUserIsNull(userToBeDelete);

        userToBeDelete.setDisabled(true);

        userRepository.save(userToBeDelete);

    }
    private void checkIfUserIsNull(User user) {
        if(user == null) {
            throw new UserNotFoundException("There is no user with this Id");
        }
    }



}
