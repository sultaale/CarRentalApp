package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.repositories.CarRepository;
import com.perz.carrentalapp.util.exceptions.CarNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


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

    public Car getById(Long id) {

        Car car = carRepository.findById(id).orElse(null);

        checkIfCarIsNull(car);

        return car;
    }

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MANAGER') || hasRole('ROLE_USER')")
    public List<Car> getAllAvailableForOrderCar(LocalDate start, LocalDate end){

        return carRepository.findAvailableCarsForDate(start, end);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Long id, Car car) {

        Car carToBeUpdate = carRepository.findById(id).orElse(null);
        checkIfCarIsNull(carToBeUpdate);

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

        checkIfCarIsNull(carToBeDelete);

        carToBeDelete.setDisabled(true);

        carRepository.save(carToBeDelete);
    }
    private void checkIfCarIsNull(Car car) {
        if(car == null) {
            throw new CarNotFoundException("There is no car with this Id");
        }
    }
}
