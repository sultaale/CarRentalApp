package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.repositories.CarRepository;
import com.perz.carrentalapp.util.exceptions.CarNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;


    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void create(Car car) {

        carRepository.save(car);
    }

    public Car findOne(Long id) {

        Car car = carRepository.findById(id).orElse(null);

        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + id);
        }

        return car;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Long id, Car car) {

        Car carToBeUpdate = carRepository.findById(id).orElse(null);

        if (carToBeUpdate == null) {
            throw new CarNotFoundException("Car not found with id: " + id);
        }
        carToBeUpdate.setBrand(car.getBrand());
        carToBeUpdate.setPrice(car.getPrice());
        carToBeUpdate.setProductionYear(car.getProductionYear());
        carToBeUpdate.setAutomatic(car.isAutomatic());

        carRepository.save(carToBeUpdate);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {

        Car carToBeDelete = carRepository.findById(id).orElse(null);

        if (carToBeDelete == null) {
            throw new CarNotFoundException("Car not found with id: " + id);
        }

        carToBeDelete.setDisabled(true);

        carRepository.save(carToBeDelete);
    }
}
