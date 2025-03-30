package com.example.taskmanagement.domain.model;

import java.util.HashMap;
import java.util.Map;

public class OwnerDTO {

    private Long id;
    private String name;
    private String email;
    private String password;

    public OwnerDTO() {
    }

    public OwnerDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getMappedSimple() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        return map;
    }
}
