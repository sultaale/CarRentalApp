package com.perz.carrentalapp.util;

import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.auth.dto.UserRegistrationDTO;
import org.modelmapper.ModelMapper;


public class Converter {

    private final static ModelMapper modelMapper = new ModelMapper();

    public static User convertFromUserDTOToUser(UserRegistrationDTO userDTO) {
        return modelMapper.map(userDTO,User.class);
    }

}
