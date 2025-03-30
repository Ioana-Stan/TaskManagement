package com.example.taskmanagement.service;

import com.example.taskmanagement.database.model.OwnerEntity;
import com.example.taskmanagement.database.repository.OwnerRepository;
import com.example.taskmanagement.domain.model.OwnerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // List all tasks (without owner information)
    public List<OwnerDTO> getAllOwners() {
        return ownerRepository.findAll().stream().map(this::mapOwnerEntity).toList();
    }

    // List all tasks assigned to a specific owner
    public OwnerDTO getOwnerById(Long id) {
        OwnerEntity ownerEntity = ownerRepository.findById(id).orElseThrow(() -> new RuntimeException("Owner not found"));
        return mapOwnerEntity(ownerEntity);
    }

    public OwnerDTO getOwnerByEmail(String email) {
        OwnerEntity ownerEntity = ownerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Owner not found"));
        return mapOwnerEntity(ownerEntity);
    }

    public void createOwner(String name, String email, String password) {
        OwnerEntity ownerEntity = new OwnerEntity(name, email, password);
        ownerRepository.save(ownerEntity);
    }

    public OwnerDTO updateOwner(Long id, String name, String email) {
        OwnerEntity ownerEntity = ownerRepository.findById(id).orElseThrow(() -> new RuntimeException("Owner not found"));

        ownerEntity.setName(name);
        ownerEntity.setEmail(email);
        ownerRepository.save(ownerEntity);

        return mapOwnerEntity(ownerEntity);
    }



    private OwnerDTO mapOwnerEntity(OwnerEntity ownerEntity) {
        return new OwnerDTO(
                ownerEntity.getName(),
                ownerEntity.getEmail(),
                ownerEntity.getPassword()
        );
    }
}
