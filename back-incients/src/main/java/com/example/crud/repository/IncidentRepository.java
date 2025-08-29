package com.example.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.model.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
}
