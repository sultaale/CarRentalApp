package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.model.User;
import com.perz.carrentalapp.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getPosition(@PathVariable int id){

        User user = positionService.findOne(id);
        return ResponseEntity.ok(position);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> update(@RequestBody Position position) {

        positionService.update(position);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {

        positionService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
