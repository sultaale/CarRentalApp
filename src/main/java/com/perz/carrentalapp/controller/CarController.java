package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.dto.CarCreateDTO;
import com.perz.carrentalapp.model.dto.CarDTO;
import com.perz.carrentalapp.model.dto.CarToBeUpdateDTO;
import com.perz.carrentalapp.service.CarService;
import com.perz.carrentalapp.util.Converter;
import com.perz.carrentalapp.util.ErrorResponse;
import com.perz.carrentalapp.util.exceptions.CarNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService carService;


    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody CarCreateDTO carCreateDTO) {

        Car car = Converter.convertFromCarCreateDTOToCar(carCreateDTO);

        carService.create(car);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getPosition(@PathVariable Long id) {

        Car car = carService.getById(id);

        CarDTO carDTO = Converter.convertFromCarToCarDTO(car);

        return ResponseEntity.ok(carDTO);
    }

    @GetMapping()
    public ResponseEntity<List<CarDTO>> getAll(@RequestParam(required = false) boolean available,
                                                   @RequestParam(required = false) LocalDate start,
                                                   @RequestParam(required = false) LocalDate end) {

        List<CarDTO> cars = carService.getAll().stream()
                .map(Converter::convertFromCarToCarDTO)
                .collect(Collectors.toList());

        if (available) {
            cars = cars.stream()
                    .filter(CarDTO::isAvailability)
                    .collect(Collectors.toList());
        }

        if(start!=null && end!=null){
            cars = carService.getAllAvailableForOrderCar(start,end).stream()
                    .map(Converter::convertFromCarToCarDTO)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(cars);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody CarToBeUpdateDTO carToBeUpdateDTO) {

        Car car = Converter.convertFromCarToBeUpdateDTOToCar(carToBeUpdateDTO);

        carService.update(id, car);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        carService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CarNotFoundException e) {

        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
