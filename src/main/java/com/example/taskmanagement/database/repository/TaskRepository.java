package com.example.taskmanagement.database.repository;

import com.example.taskmanagement.database.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT task FROM TaskEntity task WHERE task.owner.id = :ownerId")
    List<TaskEntity> findAllByOwnerId(@Param("ownerId") Long ownerId);
}
