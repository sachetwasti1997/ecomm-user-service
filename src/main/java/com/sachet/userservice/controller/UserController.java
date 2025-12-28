package com.sachet.userservice.controller;

import com.sachet.userservice.exception.UserNotFoundException;
import com.sachet.userservice.model.LoginRequest;
import com.sachet.userservice.model.SignUpRequest;
import com.sachet.userservice.model.User;
import com.sachet.userservice.service.JwtService;
import com.sachet.userservice.service.UserAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final JwtService jwtService;
    private final UserAuthenticationService userAuthenticationService;

    public UserController(JwtService jwtService, UserAuthenticationService userAuthenticationService) {
        this.jwtService = jwtService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) throws UserNotFoundException {
        return ResponseEntity.ok(userAuthenticationService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignUpRequest user) throws UserNotFoundException {
        return ResponseEntity.ok(userAuthenticationService.registerUser(user));
    }

    @GetMapping("/test-auth")
    public ResponseEntity<String> testAuth() {
        return ResponseEntity.ok("Successful");
    }

}
