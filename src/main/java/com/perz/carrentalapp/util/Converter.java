package com.perz.carrentalapp.util;

import com.perz.carrentalapp.model.Brand;
import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.Role;
import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.model.dto.BrandCreateDTO;
import com.perz.carrentalapp.model.dto.BrandDTO;
import com.perz.carrentalapp.model.dto.BrandToBeUpdateDTO;
import com.perz.carrentalapp.model.dto.CarCreateDTO;
import com.perz.carrentalapp.model.dto.CarDTO;
import com.perz.carrentalapp.model.dto.CarToBeUpdateDTO;
import com.perz.carrentalapp.model.dto.RoleCreateDTO;
import com.perz.carrentalapp.model.dto.RoleDTO;
import com.perz.carrentalapp.model.dto.RoleToBeUpdateDTO;
import com.perz.carrentalapp.model.dto.UserDTO;
import com.perz.carrentalapp.model.dto.UserRegistrationDTO;
import com.perz.carrentalapp.model.dto.UserToBeUpdateDTO;
import org.modelmapper.ModelMapper;


public class Converter {

    private final static ModelMapper modelMapper = new ModelMapper();

    public static User convertFromUserRegistrationDTOToUser(UserRegistrationDTO userDTO) {
        return modelMapper.map(userDTO,User.class);
    }

    public static User convertFromUserToUpdateDTOToUser(UserToBeUpdateDTO userToUpdateDTO) {
        return modelMapper.map(userToUpdateDTO,User.class);
    }

    public static UserDTO convertFromUserToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static Role convertFromRoleCreateDTOToRole(RoleCreateDTO roleCreateDTO) {
        return modelMapper.map(roleCreateDTO,Role.class);
    }

    public static RoleDTO convertFromRoleToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public static Role convertFromRoleToBeUpdateDTOToRole(RoleToBeUpdateDTO roleToBeUpdateDTO) {
        return modelMapper.map(roleToBeUpdateDTO,Role.class);
    }

    public static Brand convertFromBrandCreateDTOToBrand(BrandCreateDTO brandCreateDTO) {
        return modelMapper.map(brandCreateDTO,Brand.class);
    }

    public static BrandDTO convertFromBrandToBrandDTO(Brand brand) {
        return modelMapper.map(brand, BrandDTO.class);
    }

    public static Brand convertFromBrandToBeUpdateDTOToBrand(BrandToBeUpdateDTO brandToBeUpdateDTO) {
        return modelMapper.map(brandToBeUpdateDTO, Brand.class);
    }

    public static Car convertFromCarCreateDTOToCar(CarCreateDTO carCreateDTO) {
        return modelMapper.map(carCreateDTO,Car.class);
    }

    public static CarDTO convertFromCarToCarDTO(Car car) {

        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        BrandDTO brandDTO = modelMapper.map(car.getBrand(),BrandDTO.class);
        carDTO.setBrandDTO(brandDTO);

        return carDTO;
    }

    public static Car convertFromCarToBeUpdateDTOToCar(CarToBeUpdateDTO carToBeUpdateDTO) {
        return modelMapper.map(carToBeUpdateDTO,Car.class);
    }
}
