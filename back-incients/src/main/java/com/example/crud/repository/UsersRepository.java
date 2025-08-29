package com.example.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.crud.model.User;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email); 
}
