package com.perz.carrentalapp.auth.controllers;


import com.perz.carrentalapp.auth.dto.AuthenticationDTO;
import com.perz.carrentalapp.auth.dto.UserRegistrationDTO;
import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.auth.security.JWTUtil;
import com.perz.carrentalapp.auth.services.RegistrationService;
import com.perz.carrentalapp.auth.util.UserErrorResponse;
import com.perz.carrentalapp.auth.util.UserNotCreatedException;
import com.perz.carrentalapp.auth.util.UserValidator;
import com.perz.carrentalapp.util.Converter;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDTO userDTO,
                                          BindingResult bindingResult) {

        User user = Converter.convertFromUserDTOToUser(userDTO);

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
                "The username or password is incorrect",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
