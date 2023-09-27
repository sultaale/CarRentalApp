package com.perz.carrentalapp.service;

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
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public void create(Location location) {

        locationRepository.save(location);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MANAGER')|| hasRole('ROLE_USER')")
    public Location getById(Long id) {

        Location location = locationRepository.findById(id).orElse(null);

        checkIfLocationIsNull(location);

        return location;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public void update(Long id, Location location) {

        Location locationToBeUpdate = locationRepository.findById(id).orElse(null);

        checkIfLocationIsNull(locationToBeUpdate);

        locationToBeUpdate.setCity(location.getCity());
        locationToBeUpdate.setAddress(location.getAddress());

        locationRepository.save(locationToBeUpdate);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
