package com.perz.carrentalapp.service;

import com.perz.carrentalapp.model.Status;
import com.perz.carrentalapp.repositories.StatusRepository;
import com.perz.carrentalapp.util.exceptions.StatusNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StatusService {
    private final StatusRepository statusRepository;

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void create(Status status) {

        statusRepository.save(status);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MANAGER')|| hasRole('ROLE_USER')")
    public Status getById(Long id) {

        Status status = statusRepository.findById(id).orElse(null);

        checkIfStatusIsNull(status);

        return status;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MANAGER')")
    public List<Status> getAll() {

        return statusRepository.findAll();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MANAGER')")
    public void update(Long id, Status status) {

        Status statusToBeUpdate = statusRepository.findById(id).orElse(null);

        checkIfStatusIsNull(statusToBeUpdate);

        statusToBeUpdate.setName(status.getName());

        statusRepository.save(statusToBeUpdate);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {

        Status statusToBeDelete = statusRepository.findById(id).orElse(null);

        checkIfStatusIsNull(statusToBeDelete);

        statusRepository.delete(statusToBeDelete);
    }

    public Optional<Status> existingStatus(Status status) {
        return statusRepository.findByName(status.getName());
    }
    private void checkIfStatusIsNull(Status status) {
        if(status == null) {
            throw new StatusNotFoundException("There is no status with this Id");
        }
    }


}
