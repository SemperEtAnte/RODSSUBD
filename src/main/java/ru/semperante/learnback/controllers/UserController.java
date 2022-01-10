package ru.semperante.learnback.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.semperante.learnback.dto.requests.LoginRequest;
import ru.semperante.learnback.dto.requests.RegisterRequest;
import ru.semperante.learnback.dto.requests.UserEditRequest;
import ru.semperante.learnback.dto.responses.AuthResponse;
import ru.semperante.learnback.entities.User;
import ru.semperante.learnback.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/user")
@Validated
public class UserController
{
    private final UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }

    @PostMapping("register")
    public ResponseEntity<AuthResponse> doRegister(@Valid @RequestBody RegisterRequest req)
    {
        return ResponseEntity.ok(service.doRegister(req));
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> doLogin(@Valid @RequestBody LoginRequest req)
    {
        return ResponseEntity.ok(service.doLogin(req));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "jwtToken")
    public ResponseEntity<User> doMe()
    {
        return ResponseEntity.ok(service.doMe());
    }

    @PutMapping("")
    @SecurityRequirement(name = "jwtToken")
    public ResponseEntity<User> doEdit(@Valid @RequestBody UserEditRequest req)
    {
        return ResponseEntity.ok(service.doEdit(req));
    }
}
