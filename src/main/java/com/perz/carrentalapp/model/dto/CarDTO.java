package com.perz.carrentalapp.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarDTO {

    private Long id;

    private BrandDTO brand;

    private Long price;

    private Integer productionYear;

    private boolean automatic;

    private boolean availability;

    private List<ImageDTO> images;

}
