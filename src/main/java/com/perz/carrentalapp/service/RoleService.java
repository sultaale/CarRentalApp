package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Role;
import com.perz.carrentalapp.repositories.RoleRepository;
import com.perz.carrentalapp.util.exceptions.RoleNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (role == null) {
            throw new RoleNotFoundException("Role not found with id: " + id);
        }

        return role;
    }

    @Transactional
    public void update(Long id, Role role) {

        Role roleToBeUpdate = roleRepository.findById(id).orElse(null);

        if (roleToBeUpdate == null) {
            throw new RoleNotFoundException("Role not found with id: " + id);
        }

        roleToBeUpdate.setName(role.getName());

        roleRepository.save(roleToBeUpdate);
    }

    @Transactional
    public void delete(Long id) {

        Role roleToBeDelete = roleRepository.findById(id).orElse(null);

        if (roleToBeDelete == null) {
            throw new RoleNotFoundException("Role not found with id: " + id);
        }

        roleRepository.delete(roleToBeDelete);
    }
}
