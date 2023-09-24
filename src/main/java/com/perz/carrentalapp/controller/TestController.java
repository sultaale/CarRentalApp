package com.perz.carrentalapp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/test")
public class TestController {


    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MANAGER')|| hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> registration() {

        return new ResponseEntity<>(Map.of("Работает?", "Работает!"), HttpStatus.OK);
    }




}
