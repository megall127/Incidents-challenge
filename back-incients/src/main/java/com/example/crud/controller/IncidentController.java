package com.example.crud.controller;

import com.example.crud.model.Incident;
import com.example.crud.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IncidentController {

    @Autowired
    private IncidentRepository incidentRepository;

    @GetMapping("/incidents")
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }



}
