package com.perz.carrentalapp.model.dto;


import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.Location;
import com.perz.carrentalapp.model.Status;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderCreateDTO {

    private Long  carId;

    private Long UserId;

    @ManyToOne
    @JoinColumn(name = "pick_up_location_id")
    private Location pickupLocation;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name ="start_date")
    private LocalDate start;

    @Column(name ="end_date")
    private LocalDate end;

    @Column(name ="creation_date")
    private LocalDate creationDate;

    @Column(name ="date_of_change")
    private LocalDate dateChange;

    @Column(name ="total_amount")
    private Long amount;

}
