package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.security.jwt.JWTUtil;
import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.model.dto.OrderCreateDTO;
import com.perz.carrentalapp.model.dto.OrderDTO;
import com.perz.carrentalapp.model.dto.OrderToBeUpdateDTO;
import com.perz.carrentalapp.service.OrderService;
import com.perz.carrentalapp.service.UserService;
import com.perz.carrentalapp.util.Converter;
import com.perz.carrentalapp.util.ErrorResponse;
import com.perz.carrentalapp.util.exceptions.OrderNotCreatedException;
import com.perz.carrentalapp.util.exceptions.OrderNotFoundException;
import com.perz.carrentalapp.util.exceptions.OrderNotUpdatedException;
import com.perz.carrentalapp.util.exceptions.StatusNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final JWTUtil jwtUtil;


    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestHeader("Authorization") String authToken,
                                    @RequestBody OrderCreateDTO orderCreateDTO) {

        Order order = Converter.convertFromOrderCreateDTOToOrder(orderCreateDTO);

        Long userId = getUserId(authToken);

        order.setUser(userService.getById(userId));

        orderService.create(order);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getPosition(@PathVariable Long id){

        Order order = orderService.getById(id);

        OrderDTO orderDTO = Converter.convertFromOrderToOrderDTO(order);

        return ResponseEntity.ok(orderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody OrderToBeUpdateDTO orderToBeUpdateDTO) {

        Order order = Converter.convertFromOrderToBeUpdateDTOToOrder(orderToBeUpdateDTO);

        orderService.update(id,order);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<HttpStatus> updateStatus(@PathVariable Long id,
                                                        @RequestParam String status) {

        orderService.updateOrderStatus(id,status);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        orderService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Long getUserId(String authToken) {
        String jwt = authToken.substring(7);

        return jwtUtil.validateTokenAndRetrieveIdClaim(jwt);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(OrderNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(OrderNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(OrderNotUpdatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(StatusNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
