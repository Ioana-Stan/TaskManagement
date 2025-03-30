package com.example.taskmanagement.service;

import com.example.taskmanagement.database.model.TaskEntity;
import com.example.taskmanagement.database.model.OwnerEntity;
import com.example.taskmanagement.database.repository.TaskRepository;
import com.example.taskmanagement.database.repository.OwnerRepository;
import com.example.taskmanagement.domain.model.EstimationAccuracyType;
import com.example.taskmanagement.domain.model.TaskDTO;
import com.example.taskmanagement.domain.model.OwnerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final OwnerRepository ownerRepository;

    public TaskService(TaskRepository taskRepository, OwnerRepository ownerRepository) {
        this.taskRepository = taskRepository;
        this.ownerRepository = ownerRepository;
    }

    // List all tasks (without owner information)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::mapTaskEntity).toList();
    }

    // List all tasks assigned to a specific owner
    public List<TaskDTO> getTasksByOwner(Long ownerId) {
        return taskRepository.findAllByOwnerId(ownerId).stream().map(this::mapTaskEntity).toList();
    }

    public List<TaskDTO> getTasksByState(EstimationAccuracyType state) {
        return taskRepository.findAll().stream().map(this::mapTaskEntity).filter(taskDTO -> {
            return Objects.equals(taskDTO.getState(), state);
        }).toList();
    }

    // Get a task by its ID
    public TaskDTO getTaskById(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        return mapTaskEntity(taskEntity);
    }

    public TaskDTO createTask(String title, String description, int estimatedHours, int completedHours, Long ownerId) {
        OwnerEntity ownerEntity = ownerRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));

        TaskEntity taskEntity = new TaskEntity(title, description, estimatedHours, completedHours, ownerEntity);
        taskRepository.save(taskEntity);
        return mapTaskEntity(taskEntity);
    }

    public TaskDTO updateTask(Long id, String title, String description, int estimatedHours, int completedHours, Long ownerId) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        OwnerEntity ownerEntity = ownerRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));

        taskEntity.setTitle(title);
        taskEntity.setDescription(description);
        taskEntity.setEstimatedHours(estimatedHours);
        taskEntity.setCompletedHours(completedHours);
        taskEntity.setOwner(ownerEntity);
        taskRepository.save(taskEntity);

        return mapTaskEntity(taskEntity);
    }

    // Update remaining hours for a task
    public TaskDTO updateRemainingHours(Long id, int remainingHours) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        int newCompletedHours = taskEntity.getEstimatedHours() - remainingHours;

        taskEntity.setCompletedHours(newCompletedHours);
        taskRepository.save(taskEntity);

        return mapTaskEntity(taskEntity);
    }

    // Delete a task by ID
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }

        taskRepository.deleteById(id);
    }

    public EstimationAccuracyType getTaskEstimatedAccuracy(Long id) {
        TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        int estimatedHours = task.getEstimatedHours();

        EstimationAccuracyType estimationAccuracyType;

        if (estimatedHours == 0) {
            estimationAccuracyType = EstimationAccuracyType.NOT_ESTIMATED;
        } else if (0.9 * estimatedHours <= task.getCompletedHours() && task.getCompletedHours() <= 1.1 * estimatedHours) {
            estimationAccuracyType = EstimationAccuracyType.ACCURATELY_ESTIMATED;
        } else if (task.getCompletedHours() < 0.9 * estimatedHours) {
            estimationAccuracyType = EstimationAccuracyType.OVER_ESTIMATED;
        } else if (task.getCompletedHours() > 1.1 * estimatedHours) {
            estimationAccuracyType = EstimationAccuracyType.UNDER_ESTIMATED;
        } else {
            throw new RuntimeException("Could not compute estimated accuracy");
        }

        return estimationAccuracyType;
    }

    private TaskDTO mapTaskEntity(TaskEntity taskEntity) {
        return new TaskDTO(
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                getTaskEstimatedAccuracy(taskEntity.getId()),
                taskEntity.getEstimatedHours(),
                taskEntity.getCompletedHours(),
                mapOwnerEntity(taskEntity.getOwner())
        );
    }

    private OwnerDTO mapOwnerEntity(OwnerEntity ownerEntity) {
        return new OwnerDTO(
                ownerEntity.getName(),
                ownerEntity.getEmail(),
                ownerEntity.getPassword()
        );
    }
}
