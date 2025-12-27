package com.sachet.userservice.controller;

import com.sachet.userservice.model.User;
import com.sachet.userservice.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final JwtService jwtService;

    public UserController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        User user = new User();
        user.setEmail("sa@gmail.com");
        user.setPassword("wasti");
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/test-auth")
    public ResponseEntity<String> testAuth() {
        return ResponseEntity.ok("Successful");
    }

}
