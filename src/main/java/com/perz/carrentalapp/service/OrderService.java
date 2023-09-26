package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.model.Status;
import com.perz.carrentalapp.repositories.OrderRepository;
import com.perz.carrentalapp.util.exceptions.OrderNotCreatedException;
import com.perz.carrentalapp.util.exceptions.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

//@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public void create(Order order) {

        if (!order.getCar().isAvailability()) {
            throw new OrderNotCreatedException("Order was not created because car is not available");
        } else {

            order.setCar(order.getCar());
            order.setUserId(order.getUserId());
            order.setStart(order.getStart());
            order.setEnd(order.getEnd());
            order.setCreationDate(LocalDate.now());
            orderRepository.save(order);

        }
    }

    @Transactional
    public void update(Long id, Order order) {

        Order orderToBeUpdate = orderRepository.findById(id).orElse(null);

        checkIfOrderIsNull(orderToBeUpdate);

        orderToBeUpdate.setCar(order.getCar());
        orderToBeUpdate.setUserId(order.getUserId());
        orderToBeUpdate.setPickupLocation(order.getPickupLocation());
        orderToBeUpdate.setStatus(order.getStatus());
        orderToBeUpdate.setStart(order.getStart());
        orderToBeUpdate.setEnd(order.getEnd());
        orderToBeUpdate.setDateChange(LocalDate.now());
    }

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

    @Transactional
    public void archiveOrder(Order order) {
        Order orderToArchive = orderRepository.findById(order.getId()).orElse(null);
        checkIfOrderIsNull(orderToArchive);
        Status archive = new Status();
        archive.setName("Archive");
        order.setStatus(archive);
        update(orderToArchive.getId(), orderToArchive);
    }

    private void checkIfOrderIsNull(Order order) {
        if (order == null) {
            throw new OrderNotFoundException("There is no order with this Id");
        }
    }
}
