package com.perz.carrentalapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO {

    private Long id;

    private CityDTO city;

    private String address;

}
