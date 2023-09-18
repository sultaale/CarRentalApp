package com.perz.carrentalapp.auth.services;


import com.perz.carrentalapp.auth.model.User;
import com.perz.carrentalapp.auth.repositories.UsersRepository;
import com.perz.carrentalapp.auth.security.UsersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByEmail(email);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new UsersDetails(user.get());
    }
}
