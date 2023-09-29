package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Role;
import com.perz.carrentalapp.repositories.RoleRepository;
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

    public Role getById(Long id) {

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

    public Optional<Role> existingName(Role role) {
        return roleRepository.findByName(role.getName());
    }

    private void checkIfRoleIsNull(Role role) {
        if(role == null) {
            throw new RoleNotFoundException("There is no role with this Id");
        }
    }
}
