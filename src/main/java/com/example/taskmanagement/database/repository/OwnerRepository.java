package com.example.taskmanagement.database.repository;

import com.example.taskmanagement.database.model.OwnerEntity;
import com.example.taskmanagement.database.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {
    @Query("SELECT owner FROM OwnerEntity owner WHERE owner.email = :ownerEmail")
    Optional<OwnerEntity> findByEmail(@Param("ownerEmail") String ownerEmail);
}
