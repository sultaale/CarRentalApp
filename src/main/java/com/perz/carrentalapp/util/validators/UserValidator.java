package com.perz.carrentalapp.util.validators;


import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.service.UserService;
import lombok.AllArgsConstructor;;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
@AllArgsConstructor
public class UserValidator implements Validator {

    private final UserService usersService;

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
            errors.rejectValue("email", "", "User with this email already exist");
        }

        if (userPhone.isPresent()) {
            errors.rejectValue("phone", "", "User with this phone already exist");
        }
    }
}