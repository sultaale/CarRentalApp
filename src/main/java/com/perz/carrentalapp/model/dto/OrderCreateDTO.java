package com.perz.carrentalapp.model.dto;


import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
public class OrderCreateDTO {

    private Long  carId;

    private Long pickupLocationId;

    private LocalDate start;

    private LocalDate end;

}
