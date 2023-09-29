package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.auth.security.JWTUtil;
import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.model.dto.OrderCreateDTO;
import com.perz.carrentalapp.model.dto.OrderDTO;
import com.perz.carrentalapp.service.OrderService;
import com.perz.carrentalapp.service.UserService;
import com.perz.carrentalapp.util.Converter;
import com.perz.carrentalapp.util.ErrorResponse;
import com.perz.carrentalapp.util.exceptions.OrderNotCreatedException;
import com.perz.carrentalapp.util.exceptions.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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

//    @PutMapping("/{id}")
//    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
//                                             @RequestBody UserToBeUpdateDTO userToUpdateDTO) {
//
//        User user = Converter.convertFromUserToUpdateDTOToUser(userToUpdateDTO);
//
//        userService.update(id,user);
//
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
//        userService.delete(id);
//
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
//
//        UsernamePasswordAuthenticationToken authInputToken =
//                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(),
//                        authenticationDTO.getPassword());
//
//        authenticationManager.authenticate(authInputToken);
//
//        Long id = userService.getIdByEmail(authenticationDTO.getEmail());
//
//        String token = jwtUtil.generateToken(id, authenticationDTO.getEmail());
//        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
//    }

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
}
