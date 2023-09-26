package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.Location;
import com.perz.carrentalapp.repositories.LocationRepository;
import com.perz.carrentalapp.util.exceptions.CarNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository locationRepository;


    @Transactional

    public void create(Location location) {

        locationRepository.save(location);
    }

    public Location getById(Long id) {

        Location location = locationRepository.findById(id).orElse(null);

        checkIfLocationIsNull(location);

        return location;
    }

    @Transactional

    public void update(Long id, Location location) {

        Location locationToBeUpdate = locationRepository.findById(id).orElse(null);

        checkIfLocationIsNull(locationToBeUpdate);

        locationToBeUpdate.setCity(location.getCity());
        locationToBeUpdate.setAddress(location.getAddress());

        locationRepository.save(locationToBeUpdate);
    }

    @Transactional

    public void delete(Long id) {

        Location locationToBeDelete = locationRepository.findById(id).orElse(null);

        checkIfLocationIsNull(locationToBeDelete);

        locationRepository.delete(locationToBeDelete);
    }
    private void checkIfLocationIsNull(Location location) {
        if(location == null) {
            throw new CarNotFoundException("There is no location with this Id");
        }
    }
}
