package com.perz.carrentalapp.model.dto;

import com.perz.carrentalapp.model.Brand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {

    private BrandDTO brandDTO;

    private Long price;

    private Integer productionYear;

    private boolean automatic;

    private boolean availability;

}
