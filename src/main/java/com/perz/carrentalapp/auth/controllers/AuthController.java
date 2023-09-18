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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService, UserValidator userValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody  UserDTO userDTO,
                                          BindingResult bindingResult) {

        User user = convertToUser(userDTO);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {

            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errorMessage.toString());
        }

        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(),
                        authenticationDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(authenticationDTO.getEmail());
        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(BadCredentialsException e) {
        UserErrorResponse response = new UserErrorResponse(
                "Невеное имя пользователя или пароль",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    private User convertToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
