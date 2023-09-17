package com.perz.carrentalapp.model;

import jakarta.persistence.*;
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

    @Enumerated
    @Column(name ="operation_type_id")
    private Operation operation;

    @Column(name ="transaction_amount")
    private Long amount;

    @Column(name ="company_account_id")
    private Long companyAccountId;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private Account account;
}
