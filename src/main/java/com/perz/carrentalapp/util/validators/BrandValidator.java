package com.perz.carrentalapp.util.validators;


import com.perz.carrentalapp.model.Brand;
import com.perz.carrentalapp.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
@AllArgsConstructor
public class BrandValidator implements Validator {

    private final BrandService brandService;


    @Override
    public boolean supports(Class<?> aClass) {
        return Brand.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        Brand newBrand = (Brand) o;

        Optional<Brand> brandName = brandService.existingName(newBrand);
        Optional<Brand> brandModel = brandService.existingModel(newBrand);

        if (brandName.isPresent() && brandModel.isPresent()) {
            errors.rejectValue("name", "", "Brand with this name and model already" +
                    " exist");
            errors.rejectValue("model", "", "Brand with this name and model already" +
                    " exist");
        }
    }
}