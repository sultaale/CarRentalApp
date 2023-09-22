package com.perz.carrentalapp.util.validators;


import com.perz.carrentalapp.model.Role;
import com.perz.carrentalapp.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
@AllArgsConstructor
public class RoleValidator implements Validator {

    private final RoleService roleService;


    @Override
    public boolean supports(Class<?> aClass) {
        return Role.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        Role newRole = (Role) o;

        Optional<Role> role = roleService.existingRole(newRole);

        if (role.isPresent()) {
            errors.rejectValue("name", "", "Role with this name already exist");
        }
    }
}