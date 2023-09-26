package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.model.City;
import com.perz.carrentalapp.model.dto.CityCreateDTO;
import com.perz.carrentalapp.model.dto.CityDTO;
import com.perz.carrentalapp.model.dto.CityToBeUpdateDTO;
import com.perz.carrentalapp.service.CityService;
import com.perz.carrentalapp.util.Converter;
import com.perz.carrentalapp.util.ErrorMessage;
import com.perz.carrentalapp.util.ErrorResponse;
import com.perz.carrentalapp.util.exceptions.CityNotCreatedException;
import com.perz.carrentalapp.util.exceptions.CityNotFoundException;
import com.perz.carrentalapp.util.validators.CityValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;
    private final CityValidator cityValidator;


    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody CityCreateDTO cityCreateDTO,
                                             BindingResult bindingResult) {

        City city = Converter.convertFromCityCreateDTOToCity(cityCreateDTO);

        cityValidator.validate(city, bindingResult);

        if (bindingResult.hasErrors()) {

            String errorMessage = ErrorMessage.getMessage(bindingResult);

            throw new CityNotCreatedException(errorMessage);
        }

        cityService.create(city);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getPosition(@PathVariable Long id) {

        City city = cityService.getById(id);

        CityDTO cityDTO = Converter.convertFromCityToCityDTO(city);

        return ResponseEntity.ok(cityDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody CityToBeUpdateDTO cityToBeUpdateDTO) {

        City city = Converter.convertFromCityToBeUpdateDTOToCity(cityToBeUpdateDTO);

        cityService.update(id, city);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        cityService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CityNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CityNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
