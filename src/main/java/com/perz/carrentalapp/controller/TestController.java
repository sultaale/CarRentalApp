package com.perz.carrentalapp.controller;


import com.perz.carrentalapp.security.jwt.JWTUtil;
import com.perz.carrentalapp.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

    private final JWTUtil jwtUtil;
    private final OrderService orderService;

    @GetMapping()

    public ResponseEntity<?> registration(@RequestHeader("Authorization") String authToken) {

        String jwt = authToken.substring(7);

        Long id = jwtUtil.validateTokenAndRetrieveIdClaim(jwt);

        return new ResponseEntity<>(Map.of("User id: ", id), HttpStatus.OK);
    }

    @GetMapping("/check")

    public ResponseEntity<HttpStatus> order() {



        return ResponseEntity.ok().build();
    }




}
