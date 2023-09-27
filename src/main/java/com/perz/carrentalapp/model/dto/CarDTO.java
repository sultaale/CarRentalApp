package com.perz.carrentalapp.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {


    private BrandDTO brand;

    private Long price;

    private Integer productionYear;

    private boolean automatic;

    private boolean availability;

}
