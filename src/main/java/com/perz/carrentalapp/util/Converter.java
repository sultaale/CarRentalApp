package com.perz.carrentalapp.util;

import com.perz.carrentalapp.model.Brand;
import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.City;
import com.perz.carrentalapp.model.Location;
import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.model.Role;
import com.perz.carrentalapp.model.Status;
import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.model.dto.BrandCreateDTO;
import com.perz.carrentalapp.model.dto.BrandDTO;
import com.perz.carrentalapp.model.dto.BrandToBeUpdateDTO;
import com.perz.carrentalapp.model.dto.CarCreateDTO;
import com.perz.carrentalapp.model.dto.CarDTO;
import com.perz.carrentalapp.model.dto.CarToBeUpdateDTO;
import com.perz.carrentalapp.model.dto.CityCreateDTO;
import com.perz.carrentalapp.model.dto.CityDTO;
import com.perz.carrentalapp.model.dto.CityToBeUpdateDTO;
import com.perz.carrentalapp.model.dto.LocationCreateDTO;
import com.perz.carrentalapp.model.dto.LocationDTO;
import com.perz.carrentalapp.model.dto.LocationToBeUpdateDTO;
import com.perz.carrentalapp.model.dto.OrderCreateDTO;
import com.perz.carrentalapp.model.dto.OrderDTO;
import com.perz.carrentalapp.model.dto.RoleCreateDTO;
import com.perz.carrentalapp.model.dto.RoleDTO;
import com.perz.carrentalapp.model.dto.RoleToBeUpdateDTO;
import com.perz.carrentalapp.model.dto.StatusCreateDTO;
import com.perz.carrentalapp.model.dto.StatusDTO;
import com.perz.carrentalapp.model.dto.StatusToBeUpdateDTO;
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
        Car car = modelMapper.map(carCreateDTO,Car.class);
        car.setId(null);
        return car;
    }

    public static CarDTO convertFromCarToCarDTO(Car car) {

        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        BrandDTO brandDTO = modelMapper.map(car.getBrand(),BrandDTO.class);
        carDTO.setBrand(brandDTO);

        return carDTO;
    }

    public static Car convertFromCarToBeUpdateDTOToCar(CarToBeUpdateDTO carToBeUpdateDTO) {
        return modelMapper.map(carToBeUpdateDTO,Car.class);
    }

    public static Status convertFromStatusCreateDTOToStatus(StatusCreateDTO statusCreateDTO) {
        return modelMapper.map(statusCreateDTO,Status.class);
    }

    public static StatusDTO convertFromStatusToStatusDTO(Status status) {
        return modelMapper.map(status,StatusDTO.class);
    }

    public static Status convertFromStatusToBeUpdateDTOToStatus(StatusToBeUpdateDTO statusToBeUpdateDTO) {
        return modelMapper.map(statusToBeUpdateDTO,Status.class);
    }

    public static City convertFromCityCreateDTOToCity(CityCreateDTO cityCreateDTO) {
        return modelMapper.map(cityCreateDTO,City.class);
    }

    public static CityDTO convertFromCityToCityDTO(City city) {
        return modelMapper.map(city,CityDTO.class);
    }

    public static City convertFromCityToBeUpdateDTOToCity(CityToBeUpdateDTO cityToBeUpdateDTO) {
        return modelMapper.map(cityToBeUpdateDTO,City.class);
    }

    public static Location convertFromLocationCreateDTOToLocation(LocationCreateDTO locationCreateDTO) {
        Location location = modelMapper.map(locationCreateDTO,Location.class);
        location.setId(null);
        return location;
    }

    public static LocationDTO convertFromLocationToLocationDTO(Location location) {

        LocationDTO locationDTO = modelMapper.map(location, LocationDTO.class);
        CityDTO cityDTO = modelMapper.map(location.getCity(),CityDTO.class);
        locationDTO.setCity(cityDTO);

        return locationDTO;
    }

    public static Location convertFromLocationToBeUpdateDTOToLocation(LocationToBeUpdateDTO locationToBeUpdateDTO) {
        return modelMapper.map(locationToBeUpdateDTO,Location.class);
    }

    public static Order convertFromOrderCreateDTOToOrder(OrderCreateDTO orderCreateDTO) {
        Order order = modelMapper.map(orderCreateDTO, Order.class);
        order.setId(null);
        return order;
    }

    public static OrderDTO convertFromOrderToOrderDTO(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        UserDTO userDTO = modelMapper.map(order.getUser(),UserDTO.class);
        CarDTO carDTO = modelMapper.map(order.getCar(),CarDTO.class);
        LocationDTO locationDTO = modelMapper.map(order.getPickupLocation(),LocationDTO.class);
        StatusDTO statusDTO = modelMapper.map(order.getStatus(),StatusDTO.class);

        orderDTO.setUser(userDTO);
        orderDTO.setCar(carDTO);
        orderDTO.setPickupLocation(locationDTO);
        orderDTO.setStatus(statusDTO);

        return orderDTO;
    }
}
