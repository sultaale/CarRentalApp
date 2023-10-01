package com.perz.carrentalapp.controller;


import com.perz.carrentalapp.model.Status;
import com.perz.carrentalapp.model.dto.CarDTO;
import com.perz.carrentalapp.model.dto.StatusCreateDTO;
import com.perz.carrentalapp.model.dto.StatusDTO;
import com.perz.carrentalapp.model.dto.StatusToBeUpdateDTO;
import com.perz.carrentalapp.service.StatusService;
import com.perz.carrentalapp.util.Converter;
import com.perz.carrentalapp.util.ErrorMessage;
import com.perz.carrentalapp.util.ErrorResponse;
import com.perz.carrentalapp.util.exceptions.StatusNotCreatedException;
import com.perz.carrentalapp.util.exceptions.StatusNotFoundException;
import com.perz.carrentalapp.util.validators.StatusValidator;
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

import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/statuses")
public class StatusController {

    private final StatusService statusService;
    private final StatusValidator statusValidator;


    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody StatusCreateDTO statusCreateDTO,
                                             BindingResult bindingResult) {

        Status status = Converter.convertFromStatusCreateDTOToStatus(statusCreateDTO);

        statusValidator.validate(status, bindingResult);

        if (bindingResult.hasErrors()) {

            String errorMessage = ErrorMessage.getMessage(bindingResult);

            throw new StatusNotCreatedException(errorMessage);
        }

        statusService.create(status);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> getPosition(@PathVariable Long id) {

        Status status = statusService.getById(id);

        StatusDTO statusDTO = Converter.convertFromStatusToStatusDTO(status);

        return ResponseEntity.ok(statusDTO);
    }

    @GetMapping()
    public ResponseEntity<List<StatusDTO>> getAll() {

        List<StatusDTO> statuses = statusService.getAll().stream()
                .map(Converter::convertFromStatusToStatusDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(statuses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody StatusToBeUpdateDTO statusToBeUpdateDTO) {

        Status status = Converter.convertFromStatusToBeUpdateDTOToStatus(statusToBeUpdateDTO);

        statusService.update(id, status);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        statusService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(StatusNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(StatusNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
