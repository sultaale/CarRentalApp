package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Order;
import com.perz.carrentalapp.model.Role;
import com.perz.carrentalapp.repositories.RoleRepository;
import com.perz.carrentalapp.util.exceptions.OrderNotFoundException;
import com.perz.carrentalapp.util.exceptions.RoleNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public void create(Role role) {
        roleRepository.save(role);
    }

    public Role findOne(Long id) {

        Role role = roleRepository.findById(id).orElse(null);

        checkIfRoleIsNull(role);

        return role;
    }

    @Transactional
    public void update(Long id, Role role) {

        Role roleToBeUpdate = roleRepository.findById(id).orElse(null);

        checkIfRoleIsNull(roleToBeUpdate);

        roleToBeUpdate.setName(role.getName());

        roleRepository.save(roleToBeUpdate);
    }

    @Transactional
    public void delete(Long id) {

        Role roleToBeDelete = roleRepository.findById(id).orElse(null);

        checkIfRoleIsNull(roleToBeDelete);

        roleRepository.delete(roleToBeDelete);
    }

    public Optional<Role> existingRole(Role role) {
        return roleRepository.findByName(role.getName());
    }
    private static void checkIfRoleIsNull(Role role) {
        if(role == null) {
            throw new RoleNotFoundException("There is no order with this Id:");
        }
    }
}
