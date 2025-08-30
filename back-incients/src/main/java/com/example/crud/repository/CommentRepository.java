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
    
    // Buscar comentários por incidente
    List<Comment> findByIncidentIdOrderByDataCriacaoDesc(UUID incidentId);
    
    // Buscar comentários por incidente (ordem crescente)
    List<Comment> findByIncidentIdOrderByDataCriacaoAsc(UUID incidentId);
    
    // Contar comentários por incidente
    long countByIncidentId(UUID incidentId);
    
    // Buscar comentários por autor
    List<Comment> findByAutorOrderByDataCriacaoDesc(String autor);
    
    // Query personalizada para buscar comentários com informações do incidente
    @Query("SELECT c FROM Comment c WHERE c.incidentId = :incidentId ORDER BY c.dataCriacao DESC")
    List<Comment> findCommentsByIncidentId(@Param("incidentId") UUID incidentId);
    
    // Verificar se existe comentário para um incidente
    boolean existsByIncidentId(UUID incidentId);
}
