package com.perz.carrentalapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarCreateDTO {

    private Long brandId;

    private Long price;

    private Integer productionYear;

    private boolean automatic;
}
