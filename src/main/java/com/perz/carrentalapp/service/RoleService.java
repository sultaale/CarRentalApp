package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Role;
import com.perz.carrentalapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    public void create(Role role) {
        roleRepository.save(role);
    }

    public Role findOne(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(Long id, Role role) {

        Role roleToBeUpdate = roleRepository.findById(id).get();

        roleToBeUpdate.setName(role.getName());

        roleRepository.save(roleToBeUpdate);
    }

    @Transactional
    public void delete(Long id) {

        Role roleToBeDelete = roleRepository.findById(id).get();

        roleRepository.delete(roleToBeDelete);
    }
}
