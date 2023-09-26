package com.perz.carrentalapp.controller;


import com.perz.carrentalapp.model.Location;
import com.perz.carrentalapp.model.dto.LocationCreateDTO;
import com.perz.carrentalapp.model.dto.LocationDTO;
import com.perz.carrentalapp.model.dto.LocationToBeUpdateDTO;
import com.perz.carrentalapp.service.LocationService;
import com.perz.carrentalapp.util.Converter;
import com.perz.carrentalapp.util.ErrorResponse;
import com.perz.carrentalapp.util.exceptions.LocationNotFoundException;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;


    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody LocationCreateDTO locationCreateDTO) {

        Location location = Converter.convertFromLocationCreateDTOToLocation(locationCreateDTO);

        locationService.create(location);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getPosition(@PathVariable Long id){

        Location location = locationService.getById(id);

        LocationDTO locationDTO = Converter.convertFromLocationToLocationDTO(location);

        return ResponseEntity.ok(locationDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody LocationToBeUpdateDTO locationToBeUpdateDTO) {

        Location location = Converter.convertFromLocationToBeUpdateDTOToLocation(locationToBeUpdateDTO);

        locationService.update(id,location);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        locationService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }



    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(LocationNotFoundException e) {

        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
