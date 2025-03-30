package com.example.taskmanagement.controller;

import com.example.taskmanagement.domain.model.OwnerDTO;
import com.example.taskmanagement.service.OwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public List<Map<String, Object>> getAllOwners() {
        return ownerService.getAllOwners().stream().map(OwnerDTO::getMappedSimple).toList();
    }

    @GetMapping("/owner")
    public Map<String, Object> getOwnerById(@RequestParam Long id) {
        return ownerService.getOwnerById(id).getMappedSimple();
    }

    @PutMapping("/update")
    public OwnerDTO updateOwner(@RequestParam Long id, @RequestParam String name, @RequestParam String email) {
        return ownerService.updateOwner(id, name, email);
    }
}
