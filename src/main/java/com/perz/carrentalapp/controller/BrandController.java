package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.model.Brand;
import com.perz.carrentalapp.model.dto.BrandCreateDTO;
import com.perz.carrentalapp.model.dto.BrandDTO;
import com.perz.carrentalapp.model.dto.BrandToBeUpdateDTO;
import com.perz.carrentalapp.service.BrandService;
import com.perz.carrentalapp.util.BrandErrorResponse;
import com.perz.carrentalapp.util.exceptions.BrandNotFoundException;
import com.perz.carrentalapp.util.Converter;
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
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;


    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody BrandCreateDTO brandCreateDTO) {

        Brand brand = Converter.convertFromBrandCreateDTOToBrand(brandCreateDTO);

        brandService.create(brand);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getPosition(@PathVariable Long id){

        Brand brand = brandService.findOne(id);

        BrandDTO brandDTO = Converter.convertFromBrandToBrandDTO(brand);

        return ResponseEntity.ok(brandDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody BrandToBeUpdateDTO brandToBeUpdateDTO) {

        Brand brand = Converter.convertFromBrandToBeUpdateDTOToBrand(brandToBeUpdateDTO);

        brandService.update(id,brand);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        brandService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<BrandErrorResponse> handleException(BrandNotFoundException e) {

        BrandErrorResponse response = new BrandErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
