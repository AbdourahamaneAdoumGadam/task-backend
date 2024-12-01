package com.fad.tasktracker.controller.auth;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fad.tasktracker.entities.User;
import com.fad.tasktracker.entities.others.AuthenticationResponse;
import com.fad.tasktracker.entities.others.LoginResponse;
import com.fad.tasktracker.service.AuthenticationService;
import com.fad.tasktracker.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User request) {
        AuthenticationResponse authResponse = authService.authenticate(request);
        Optional<User> user = userService.findByEmail(request.getUsername());

        LoginResponse loginResponse = new LoginResponse(authResponse, user.get());
        return ResponseEntity.ok(loginResponse);
    }
}