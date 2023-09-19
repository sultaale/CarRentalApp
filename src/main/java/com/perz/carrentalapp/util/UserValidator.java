package com.perz.carrentalapp.util;


import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class UserValidator implements Validator {

    private final UserService usersService;

    @Autowired
    public UserValidator(UserService usersService) {
        this.usersService = usersService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        User newUser = (User) o;

        Optional<User> userEmail = usersService.existingEmail(newUser);

        Optional<User> userPhone = usersService.existingPhone(newUser);

        if (userEmail.isPresent()) {
            errors.rejectValue("email", "", "Пользователь с таким email уже существует");
        }

        if (userPhone.isPresent()) {
            errors.rejectValue("phone", "", "Пользователь с таким телефоном уже существует");
        }
    }
}