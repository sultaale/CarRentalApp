package com.perz.carrentalapp.auth.controllers;


import com.perz.carrentalapp.auth.dto.AuthenticationDTO;
import com.perz.carrentalapp.auth.dto.UserDTO;
import com.perz.carrentalapp.auth.model.User;
import com.perz.carrentalapp.auth.security.JWTUtil;
import com.perz.carrentalapp.auth.services.RegistrationService;
import com.perz.carrentalapp.auth.util.UserErrorResponse;
import com.perz.carrentalapp.auth.util.UserNotCreatedException;
import com.perz.carrentalapp.auth.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/test")
public class TestController {






    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> registration() {

        return new ResponseEntity<>(Map.of("Работает?", "Работает!"), HttpStatus.OK);
    }



}
