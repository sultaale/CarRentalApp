package com.perz.carrentalapp.service;


import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.repositories.RoleRepository;
import com.perz.carrentalapp.repositories.UserRepository;
import com.perz.carrentalapp.util.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_USER").get());
        user.setDisabled(false);
        usersRepository.save(user);
    }

    public User findOne(Long id) {

        User user = usersRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        return user;
    }

    public Optional<User> existingEmail(User user) {
        return usersRepository.findByEmail(user.getEmail());
    }

    public Optional<User> existingPhone(User user) {
        return usersRepository.findByPhone(user.getPhone());
    }

    @Transactional
    public void update(Long id, User user) {

        User userToBeUpdate = usersRepository.findById(id).orElse(null);

        if (userToBeUpdate == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
            userToBeUpdate.setFirstname(user.getFirstname());
            userToBeUpdate.setLastname(user.getLastname());
            userToBeUpdate.setEmail(user.getEmail());
            userToBeUpdate.setPhone(user.getPhone());
            userToBeUpdate.setPassword(user.getPassword());

            usersRepository.save(userToBeUpdate);
    }

    @Transactional
    public void delete(Long id) {

        User userToBeDelete = usersRepository.findById(id).orElse(null);

        if (userToBeDelete == null) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        userToBeDelete.setDisabled(true);

        usersRepository.save(userToBeDelete);

    }
}
