package com.example.crud.repository;

import com.example.crud.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByIncidentIdOrderByDataCriacaoDesc(UUID incidentId);
    
    List<Comment> findByIncidentIdOrderByDataCriacaoAsc(UUID incidentId);
    
    long countByIncidentId(UUID incidentId);
    

    List<Comment> findByAutorOrderByDataCriacaoDesc(String autor);
    
    @Query("SELECT c FROM Comment c WHERE c.incidentId = :incidentId ORDER BY c.dataCriacao DESC")
    List<Comment> findCommentsByIncidentId(@Param("incidentId") UUID incidentId);
    
    boolean existsByIncidentId(UUID incidentId);
}
