package com.example.crud.controller;

import com.example.crud.model.Incident;
import com.example.crud.repository.IncidentRepository;
import com.example.crud.repository.CommentRepository;
import com.example.crud.dto.IncidentStatsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private IncidentRepository incidentRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/incidents")
    public ResponseEntity<IncidentStatsResponse> getIncidentStats() {
       
        List<Incident> incidents = incidentRepository.findAll();
        
       
        Long totalIncidents = (long) incidents.size();
        
    //    tentar otimizar esse codigo se der tempo
        Map<String, Long> incidentsByStatus = incidents.stream()
            .collect(Collectors.groupingBy(
                incident -> incident.getStatus().name(),
                Collectors.counting()
            ));
        
      
        Map<String, Long> incidentsByPriority = incidents.stream()
            .collect(Collectors.groupingBy(
                incident -> incident.getPrioridade().name(),
                Collectors.counting()
            ));
        
      
        Map<String, Long> incidentsByResponsible = incidents.stream()
            .collect(Collectors.groupingBy(
                Incident::getResponsavelEmail,
                Collectors.counting()
            ));
        
       
        Long totalComments = commentRepository.count();
        
       
        Double averageCommentsPerIncident = totalIncidents > 0 ? 
            (double) totalComments / totalIncidents : 0.0;
        
       
        IncidentStatsResponse stats = new IncidentStatsResponse(
            totalIncidents,
            incidentsByStatus,
            incidentsByPriority,
            incidentsByResponsible,
            totalComments,
            averageCommentsPerIncident
        );
        
        return ResponseEntity.ok(stats);
    }
}
