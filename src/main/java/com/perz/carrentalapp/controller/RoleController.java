package com.perz.carrentalapp.controller;

import com.perz.carrentalapp.model.Role;
import com.perz.carrentalapp.model.dto.RoleCreateDTO;
import com.perz.carrentalapp.model.dto.RoleDTO;
import com.perz.carrentalapp.model.dto.RoleToBeUpdateDTO;
import com.perz.carrentalapp.service.RoleService;
import com.perz.carrentalapp.util.Converter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;


    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody RoleCreateDTO roleCreateDTO) {

        Role role = Converter.convertFromRoleCreateDTOToRole(roleCreateDTO);

        roleService.create(role);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getPosition(@PathVariable Long id){

        Role role = roleService.findOne(id);

        if(role==null){
            return ResponseEntity.notFound().build();
        }

        RoleDTO roleDTO = Converter.convertFromRoleToRoleDTO(role);

        return ResponseEntity.ok(roleDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody RoleToBeUpdateDTO roleToBeUpdateDTO) {

        Role role = Converter.convertFromRoleToBeUpdateDTOToRole(roleToBeUpdateDTO);

        roleService.update(id,role);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        roleService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
