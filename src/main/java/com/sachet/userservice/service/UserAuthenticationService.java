package com.sachet.userservice.service;

import com.sachet.userservice.exception.UserNotFoundException;
import com.sachet.userservice.model.LoginRequest;
import com.sachet.userservice.model.Roles;
import com.sachet.userservice.model.SignUpRequest;
import com.sachet.userservice.model.User;
import com.sachet.userservice.repo.RoleRepository;
import com.sachet.userservice.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class UserAuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public UserAuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                     JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.objectMapper = new ObjectMapper();;
    }

    public String registerUser(SignUpRequest signUpRequest) {
        User user = objectMapper.convertValue(signUpRequest, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        Roles role;
        if (!roleRepository.existsRolesByUserRole(signUpRequest.getRoles().getUserRole())) {
             role = roleRepository.save(signUpRequest.getRoles());
        }else {
            role = roleRepository.getRolesByUserRole(signUpRequest.getRoles().getUserRole()).get(0);
        }
        user.setRoles(role);
        User userSaved = userRepository.save(user);
        return "Successfully Registered User";
    }

    public String login(LoginRequest loginRequest) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new UserNotFoundException("No user found with the given credentials");
        }
        boolean userFound = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!userFound) {
            throw new UserNotFoundException("No user found with given credentials");
        }
        return jwtService.generateToken(user);
    }
}
