package com.example.taskmanagement.controller;

import com.example.taskmanagement.domain.model.EstimationAccuracyType;
import com.example.taskmanagement.domain.model.TaskDTO;
import com.example.taskmanagement.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/extended")
    public List<Map<String, Object>> getExtendedTasks() {
        return taskService.getAllTasks().stream().map(TaskDTO::getMappedExtended).toList();
    }

    @GetMapping("/state")
    public List<Map<String, Object>> getTasksByState(@RequestParam String state) {
        EstimationAccuracyType estimationAccuracyType = EstimationAccuracyType.valueOf(state);
        return taskService.getTasksByState(estimationAccuracyType).stream().map(TaskDTO::getMappedExtended).toList();
    }

    @GetMapping("/owner")
    public List<Map<String, Object>> getTasksByOwner(@RequestParam Long ownerId) {
        return taskService.getTasksByOwner(ownerId).stream().map(TaskDTO::getMappedSimple).toList();
    }

    @PutMapping("/create")
    public Map<String, Object> createTask(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int estimatedHours,
            @RequestParam int completedHours,
            @RequestParam Long ownerId
    ) {
        return taskService.createTask(
                title,
                description,
                estimatedHours,
                completedHours,
                ownerId
        ).getMappedSimple();
    }

    @PutMapping("/update")
    public Map<String, Object> updateTask(
            @RequestParam Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int estimatedHours,
            @RequestParam int completedHours,
            @RequestParam Long ownerId
    ) {
        return taskService.updateTask(
                id,
                title,
                description,
                estimatedHours,
                completedHours,
                ownerId
        ).getMappedSimple();
    }

    // ---------------------  Extra endpoint helpful during development ---------------------
    @GetMapping
    public List<Map<String, Object>> getAllTasks() {
        return taskService.getAllTasks().stream().map(TaskDTO::getMappedWithoutOwner).toList();
    }

    @GetMapping("/task")
    public Map<String, Object> getTaskById(@RequestParam Long id) {
        return taskService.getTaskById(id).getMappedExtended();
    }

    @PutMapping("/remaining-hours")
    public Map<String, Object> updateRemainingHours(@RequestParam Long id, @RequestParam int hours) {
        return taskService.updateRemainingHours(id, hours).getMappedExtended();
    }

    @DeleteMapping
    public void deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/estimated-accuracy")
    public String getTaskEstimatedAccuracy(@RequestParam Long id) {
        return taskService.getTaskEstimatedAccuracy(id).label;
    }
}
