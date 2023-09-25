package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Car;
import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.model.Status;
import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.repositories.OrderRepository;
import com.perz.carrentalapp.util.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Transactional
    public boolean addNewOrder(Order order) {
        if(!order.getCar().isAvailability()) return false;
        else {
            Order orderToSave = new Order();
            orderToSave.setCar(order.getCar());
            orderToSave.setUserId(order.getUserId());
            orderToSave.setStart(order.getStart());
            orderToSave.setEnd(order.getEnd());
            orderToSave.setCreationDate(LocalDate.now());
            orderRepository.save(orderToSave);
            return true;
        }
    }
    @Transactional
    public void updateOrder(Long id, Order order) {
        Order orderToChange = orderRepository.findById(id).orElse(null);
        checkIfOrderIsNull(orderToChange);
        orderToChange.setCar(order.getCar());
        orderToChange.setUserId(order.getUserId());
        orderToChange.setPickupLocation(order.getPickupLocation());
        orderToChange.setStatus(order.getStatus());
        orderToChange.setStart(order.getStart());
        orderToChange.setEnd(order.getEnd());
        orderToChange.setDateChange(LocalDate.now());
    }
    public Order getById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        checkIfOrderIsNull(order);
        return order;
    }
    public List<Order> getAllActiveOrders() {
        return orderRepository.findAllByStatus(new Status());
    }
    public List<Order> getAllOrdersByUser(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }
    public List<Order> getAllOrdersByCar(Car car) {
        return orderRepository.findAllByCar(car);
    }
    @Transactional
    public void archiveOrder(Order order) {
        Order orderToArchive = orderRepository.findById(order.getId()).orElse(null);
        checkIfOrderIsNull(orderToArchive);
        Status archive = new Status();
        archive.setName("Archive");
        order.setStatus(archive);
        updateOrder(orderToArchive.getId(), orderToArchive);
    }
    private static void checkIfOrderIsNull(Order order) {
        if(order == null) {
            throw new OrderNotFoundException("There is no order with this Id:");
        }
    }
}
