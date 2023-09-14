package com.perz.carrentalapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="car_id")
    private Long car_id;
    @Column(name ="users_id")
    private Long users_id;
    @Column(name ="pick_up_location_id")
    private Long pickupLocationid;
    @Column(name ="drop_off_location_id")
    private Long dropoffLocationid;
    @Column(name ="status_id")
    private Long statusid;
    @Column(name ="start_date")
    private LocalDate start;
    @Column(name ="end_date")
    private LocalDate end;
    @Column(name ="creation_date")
    private LocalDate creationDate;
    @Column(name ="date_of_change")
    private LocalDate dateChange;
    private int amount;


}
