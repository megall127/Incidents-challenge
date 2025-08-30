package com.example.crud.controller;

import com.example.crud.model.User;
import com.example.crud.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;


    @GetMapping
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }
}
