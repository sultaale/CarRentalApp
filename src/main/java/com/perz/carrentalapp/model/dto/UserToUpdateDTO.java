package com.perz.carrentalapp.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserToUpdateDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private String password;

}
