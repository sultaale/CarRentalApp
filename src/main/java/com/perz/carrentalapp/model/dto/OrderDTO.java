package com.perz.carrentalapp.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO {

    private Long id;

    private UserDTO user;

    private CarDTO car;

    private LocalDate start;

    private LocalDate end;

    private LocationDTO pickupLocation;

    private Long amount;

    private LocalDate creationDate;

    private LocalDate dateChange;

    private StatusDTO status;

}
