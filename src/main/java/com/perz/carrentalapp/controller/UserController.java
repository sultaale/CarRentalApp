package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.model.dto.AuthenticationDTO;
import com.perz.carrentalapp.model.dto.UserRegistrationDTO;
import com.perz.carrentalapp.auth.security.JWTUtil;
import com.perz.carrentalapp.model.dto.UserToUpdateDTO;
import com.perz.carrentalapp.service.UserService;
import com.perz.carrentalapp.util.UserErrorResponse;
import com.perz.carrentalapp.util.UserNotCreatedException;
import com.perz.carrentalapp.util.UserValidator;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ResponseEntity<?> create(@RequestBody UserRegistrationDTO userDTO,
                                          BindingResult bindingResult) {

        User user = Converter.convertFromUserRegistrationDTOToUser(userDTO);

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

        userService.create(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getPosition(@PathVariable long id){

        User user = userService.findOne(id);

        if(user==null){
            return ResponseEntity.notFound().build();
        }

        UserDTO userDTO = Converter.convertFromUserToUserDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> update(@RequestBody UserToUpdateDTO userToUpdateDTO) {

        User user = Converter.convertFromUserToUpdateDTOToUser(userToUpdateDTO);

        userService.update(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
//
//
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

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
