package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.model.dto.AuthenticationDTO;
import com.perz.carrentalapp.model.dto.UserRegistrationDTO;
import com.perz.carrentalapp.auth.security.JWTUtil;
import com.perz.carrentalapp.model.dto.UserToBeUpdateDTO;
import com.perz.carrentalapp.service.UserService;
import com.perz.carrentalapp.util.ErrorMessage;
import com.perz.carrentalapp.util.ErrorResponse;
import com.perz.carrentalapp.util.exceptions.UserNotCreatedException;
import com.perz.carrentalapp.util.exceptions.UserNotFoundException;
import com.perz.carrentalapp.util.validators.UserValidator;
import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.model.dto.UserDTO;
import com.perz.carrentalapp.util.Converter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserRegistrationDTO userRegistrationDTO,
                                          BindingResult bindingResult) {

        User user = Converter.convertFromUserRegistrationDTOToUser(userRegistrationDTO);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {

            String errorMessage = ErrorMessage.getMessage(bindingResult);

            throw new UserNotCreatedException(errorMessage);
        }

        userService.create(user);

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getPosition(@PathVariable Long id){

        User user = userService.getById(id);

        UserDTO userDTO = Converter.convertFromUserToUserDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody UserToBeUpdateDTO userToUpdateDTO) {

        User user = Converter.convertFromUserToUpdateDTOToUser(userToUpdateDTO);

        userService.update(id,user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(),
                        authenticationDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        User user = userService.getByEmail(authenticationDTO.getEmail());

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BadCredentialsException e) {
        ErrorResponse response = new ErrorResponse(
                "The username or password is incorrect",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
