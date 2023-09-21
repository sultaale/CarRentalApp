package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Brand;
import com.perz.carrentalapp.repositories.BrandRepository;
import com.perz.carrentalapp.util.exceptions.BrandNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;


    @Transactional
    public void create(Brand brand) {
        brandRepository.save(brand);
    }

    public Brand findOne(Long id) {

        Brand brand = brandRepository.findById(id).orElse(null);

        if (brand == null) {
            throw new BrandNotFoundException("Brand not found with id: " + id);
        }

        return brand;
    }

    @Transactional
    public void update(Long id, Brand brand) {

        Brand brandToBeUpdate = brandRepository.findById(id).orElse(null);

        if (brandToBeUpdate == null) {
            throw new BrandNotFoundException("Brand not found with id: " + id);
        }

        brandToBeUpdate.setName(brand.getName());
        brandToBeUpdate.setModel(brand.getModel());

        brandRepository.save(brandToBeUpdate);
    }

    @Transactional
    public void delete(Long id) {

        Brand brandToBeDelete = brandRepository.findById(id).orElse(null);

        if (brandToBeDelete == null) {
            throw new BrandNotFoundException("Brand not found with id: " + id);
        }

        brandRepository.delete(brandToBeDelete);
    }
}
