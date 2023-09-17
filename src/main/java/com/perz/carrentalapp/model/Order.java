package com.perz.carrentalapp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name ="users_id")
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

    private Long amount;
}
