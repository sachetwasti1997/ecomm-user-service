package com.sachet.userservice.controller;

import com.sachet.userservice.exception.UserNotFoundException;
import com.sachet.userservice.model.LoginRequest;
import com.sachet.userservice.model.SignUpRequest;
import com.sachet.userservice.model.User;
import com.sachet.userservice.service.JwtService;
import com.sachet.userservice.service.UserAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*")
public class UserAuthenticationController {

    private final JwtService jwtService;
    private final UserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(JwtService jwtService, UserAuthenticationService userAuthenticationService) {
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

    @GetMapping("/fetch")
    public ResponseEntity<User> fetchUser(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok(userAuthenticationService.getUser(principal.getName()));
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
