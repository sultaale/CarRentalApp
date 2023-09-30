package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.repositories.CarRepository;
import com.perz.carrentalapp.repositories.OrderRepository;
import com.perz.carrentalapp.repositories.StatusRepository;
import com.perz.carrentalapp.util.exceptions.OrderNotCreatedException;
import com.perz.carrentalapp.util.exceptions.OrderNotFoundException;
import com.perz.carrentalapp.util.exceptions.OrderNotUpdatedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    private final CarRepository carRepository;

    @Transactional
    public void create(Order order) {

        checkAvailableCarByDates(order);

        order.setStatus(statusRepository.findByName("STATUS_CREATED").get());
        order.setCreationDate(LocalDate.now());
        order.setDateChange(LocalDate.now());
        order.setAmount(getAmount(order));

        orderRepository.save(order);
    }

    public Order getById(Long id) {

        Order order = orderRepository.findById(id).orElse(null);
        checkIfOrderIsNull(order);
        return order;
    }

    @Transactional
    public void update(Long id, Order order) {

        Order orderToBeUpdate = orderRepository.findById(id).orElse(null);
        checkIfOrderIsNull(orderToBeUpdate);

        orderToBeUpdate.setPickupLocation(order.getPickupLocation());
        orderToBeUpdate.setStart(order.getStart());
        orderToBeUpdate.setEnd(order.getEnd());
        orderToBeUpdate.setDateChange(LocalDate.now());

        checkAvailableCarByDatesForUpdate(orderToBeUpdate);

        orderRepository.save(orderToBeUpdate);
    }

    @Transactional
    public void delete(Long id) {

        Order orderToBeDelete = orderRepository.findById(id).orElse(null);

        checkIfOrderIsNull(orderToBeDelete);

        orderToBeDelete.setStatus(statusRepository.findByName("STATUS_CANCELED").get());
        orderToBeDelete.setDateChange(LocalDate.now());

        orderRepository.save(orderToBeDelete);

    }
    private void checkIfOrderIsNull(Order order) {
        if (order == null) {
            throw new OrderNotFoundException("There is no order with this Id");
        }
    }

    private Long getAmount(Order order) {
        Long rentalDuration = ChronoUnit.DAYS.between(order.getStart(), order.getEnd());
        Long carRentalPrice = carRepository.findById(order.getCar().getId()).get().getPrice();
        Long amount = rentalDuration * carRentalPrice;
        return amount;
    }

    private void checkAvailableCarByDates(Order order) {

        Long carId = order.getCar().getId();
        LocalDate start = order.getStart();
        LocalDate end = order.getEnd();

        List<Order> orders = orderRepository.findActiveOrdersByCarAndDate(carId, start, end);

        if (!orders.isEmpty()) {
            throw new OrderNotCreatedException("This car is not available for this dates");
        }
    }

    private void checkAvailableCarByDatesForUpdate(Order order) {

        Long carId = order.getCar().getId();
        LocalDate start = order.getStart();
        LocalDate end = order.getEnd();

        List<Order> orders = orderRepository.findActiveOrdersByCarAndDate(carId, start, end);
        orders = orders.stream().filter(o -> !o.getId().equals(order.getId())).collect(Collectors.toList());

        if (!orders.isEmpty()) {
            throw new OrderNotUpdatedException("This car is not available for this dates");
        }
    }
}
