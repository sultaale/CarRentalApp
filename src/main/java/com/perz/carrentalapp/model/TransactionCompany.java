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
@Table(name = "company_transaction")
public class TransactionCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="transaction_date")
    private LocalDate date;
    @Column(name ="operation_type_id")
    private Long operationTypeid;
    @Column(name ="transaction_amount")
    private Long amount;


    @Column(name ="company_account_id")
    private Long companyAccountid;
    @Column(name ="user_account_id")
    private Long userAccountid;


}
