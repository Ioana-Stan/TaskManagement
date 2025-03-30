package com.example.taskmanagement.domain.model;


import java.util.HashMap;
import java.util.Map;

public class TaskDTO {

    private String title;
    private String description;
    private EstimationAccuracyType state;
    private int estimatedHours;
    private int completedHours;
    private OwnerDTO owner;

    // Constructors, Getters, and Setters
    public TaskDTO() {
    }

    public TaskDTO(String title, String description, EstimationAccuracyType state, int estimatedHours, int completedHours, OwnerDTO owner) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.estimatedHours = estimatedHours;
        this.completedHours = completedHours;
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EstimationAccuracyType getState() {
        return state;
    }

    public void setState(EstimationAccuracyType state) {
        this.state = state;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public int getCompletedHours() {
        return completedHours;
    }

    public void setCompletedHours(int completedHours) {
        this.completedHours = completedHours;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }


    public Map<String, Object> getMappedWithoutOwner() {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("title", title);
        taskMap.put("description", description);
        taskMap.put("estimatedHours", estimatedHours);
        taskMap.put("completedHours", completedHours);
        taskMap.put("ownerName", owner.getName());
        return taskMap;
    }

    public Map<String, Object> getMappedSimple() {
        Map<String, Object> map = new HashMap<>();

        map.put("title", title);
        map.put("description", description);
        map.put("estimatedHours", estimatedHours);
        map.put("completedHours", completedHours);
        map.put("owner", owner.getMappedSimple());
        return map;
    }

    public Map<String, Object> getMappedExtended() {
        Map<String, Object> map = new HashMap<>();

        map.put("title", title);
        map.put("description", description);
        map.put("state", state);
        map.put("remainingEffort", estimatedHours - completedHours);
        map.put("estimatedHours", estimatedHours);
        map.put("completedHours", completedHours);
        return map;
    }
}
