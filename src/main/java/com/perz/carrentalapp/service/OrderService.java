package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.repositories.CarRepository;
import com.perz.carrentalapp.repositories.OrderRepository;
import com.perz.carrentalapp.repositories.StatusRepository;
import com.perz.carrentalapp.util.exceptions.OrderNotCreatedException;
import com.perz.carrentalapp.util.exceptions.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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


//    @Transactional
//    public void update(Long id, Order order) {
//
//
//
//        Order orderToBeUpdate = orderRepository.findById(id).orElse(null);
//
//        checkIfOrderIsNull(orderToBeUpdate);
//
//        orderToBeUpdate.setCar(order.getCar());
//        orderToBeUpdate.setUserId(order.getUserId());
//        orderToBeUpdate.setPickupLocation(order.getPickupLocation());
//        orderToBeUpdate.setStatus(order.getStatus());
//        orderToBeUpdate.setStart(order.getStart());
//        orderToBeUpdate.setEnd(order.getEnd());
//        orderToBeUpdate.setDateChange(LocalDate.now());
//    }

    public Order getById(Long id) {

        Order order = orderRepository.findById(id).orElse(null);
        checkIfOrderIsNull(order);
        return order;
    }


//    public List<Order> getAllActiveOrders() {
//        return orderRepository.findAllByStatus(new Status());
//    }

//    public List<Order> getAllOrdersByUser(Long userId) {
//        return orderRepository.findAllByUserId(userId);
//    }
//
//    public List<Order> getAllOrdersByCar(Car car) {
//        return orderRepository.findAllByCar(car);
//    }

//    @Transactional
//    public void archiveOrder(Order order) {
//        Order orderToArchive = orderRepository.findById(order.getId()).orElse(null);
//        checkIfOrderIsNull(orderToArchive);
//        Status archive = new Status();
//        archive.setName("Archive");
//        order.setStatus(archive);
//        update(orderToArchive.getId(), orderToArchive);
//    }

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

    private void checkAvailableCarByDates(Order order){

        Long carId = order.getCar().getId();
        LocalDate start = order.getStart();
        LocalDate end = order.getEnd();

        List<Order> orders = orderRepository.findActiveOrdersByCarAndDate(carId,start,end);

        if(!orders.isEmpty()){
            throw new OrderNotCreatedException("This car is not available for this dates");
        }
    }
}
