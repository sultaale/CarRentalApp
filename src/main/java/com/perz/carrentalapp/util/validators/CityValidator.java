package com.perz.carrentalapp.util.validators;


import com.perz.carrentalapp.model.City;
import com.perz.carrentalapp.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
@AllArgsConstructor
public class CityValidator implements Validator {

    private final CityService cityService;


    @Override
    public boolean supports(Class<?> aClass) {
        return City.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {

        City newCity = (City) o;

        Optional<City> city = cityService.existingName(newCity);

        if (city.isPresent()) {
            errors.rejectValue("name", "", "City with this name already exist");
        }
    }
}