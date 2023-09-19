package com.perz.carrentalapp.auth.services;


import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.repositories.UserRepository;
import com.perz.carrentalapp.auth.security.UsersDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UsersDetailsService implements UserDetailsService {
    private final UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByEmail(email);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User is not found");

        return new UsersDetails(user.get());
    }
}
