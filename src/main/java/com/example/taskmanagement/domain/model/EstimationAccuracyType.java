package com.example.taskmanagement.domain.model;

public enum EstimationAccuracyType {
    NOT_ESTIMATED("NOT_ESTIMATED"),
    ACCURATELY_ESTIMATED("ACCURATELY_ESTIMATED"),
    OVER_ESTIMATED("OVER_ESTIMATED"),
    UNDER_ESTIMATED("UNDER_ESTIMATED");

    public final String label;

    private EstimationAccuracyType(String label) {
        this.label = label;
    }
}
