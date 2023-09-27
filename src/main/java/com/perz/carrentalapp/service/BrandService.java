package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Brand;
import com.perz.carrentalapp.repositories.BrandRepository;
import com.perz.carrentalapp.util.exceptions.BrandNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;


    @Transactional
    public void create(Brand brand) {
        brandRepository.save(brand);
    }

    public Brand getById(Long id) {

        Brand brand = brandRepository.findById(id).orElse(null);

        checkIfBrandIsNull(brand);

        return brand;
    }

    @Transactional
    public void update(Long id, Brand brand) {

        Brand brandToBeUpdate = brandRepository.findById(id).orElse(null);
        checkIfBrandIsNull(brandToBeUpdate);
        brandToBeUpdate.setName(brand.getName());
        brandToBeUpdate.setModel(brand.getModel());

        brandRepository.save(brandToBeUpdate);
    }

    @Transactional
    public void delete(Long id) {

        Brand brandToBeDelete = brandRepository.findById(id).orElse(null);
        checkIfBrandIsNull(brandToBeDelete);

        brandRepository.delete(brandToBeDelete);
    }

    public Brand getByName(String name) {
        return brandRepository.findByName(name).orElse(null);
    }

    public Brand getByModel(String model) {
        return brandRepository.findByModel(model).orElse(null);
    }

    private void checkIfBrandIsNull(Brand brand) {
        if(brand == null) {
            throw new BrandNotFoundException("There is no brand with this Id");
        }
    }
}
