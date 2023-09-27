package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.City;
import com.perz.carrentalapp.repositories.CityRepository;
import com.perz.carrentalapp.util.exceptions.CityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CityService {
    private final CityRepository cityRepository;

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void create(City city) {

        cityRepository.save(city);
    }

    public City getById(Long id) {

        City city = cityRepository.findById(id).orElse(null);

        checkIfCityIsNull(city);

        return city;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Long id, City status) {

        City cityToBeUpdate = cityRepository.findById(id).orElse(null);

        checkIfCityIsNull(cityToBeUpdate);

        cityToBeUpdate.setName(status.getName());

        cityRepository.save(cityToBeUpdate);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {

        City cityToBeDelete = cityRepository.findById(id).orElse(null);

        checkIfCityIsNull(cityToBeDelete);

        cityRepository.delete(cityToBeDelete);
    }

    public City getByName(String name) {
        return cityRepository.findByName(name).orElse(null);
    }

    private void checkIfCityIsNull(City city) {
        if(city == null) {
            throw new CityNotFoundException("There is no city with this Id");
        }
    }
}
