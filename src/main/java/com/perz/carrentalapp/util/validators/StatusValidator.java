package com.perz.carrentalapp.util.validators;


import com.perz.carrentalapp.model.Status;
import com.perz.carrentalapp.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class StatusValidator implements Validator {

    private final StatusService statusService;


    @Override
    public boolean supports(Class<?> aClass) {
        return Status.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {

        Status newStatus = (Status) o;

        Status status = statusService.getByName(newStatus.getName());

        if (status != null) {
            errors.rejectValue("name", "", "Status with this name already exist");
        }
    }
}