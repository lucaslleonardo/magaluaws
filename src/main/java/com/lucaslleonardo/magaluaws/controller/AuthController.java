package com.lucaslleonardo.magaluaws.controller;


import com.lucaslleonardo.magaluaws.dto.dtoPostRequest.LoginRequest;
import com.lucaslleonardo.magaluaws.dto.dtoResponse.TokenResponse;
import com.lucaslleonardo.magaluaws.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        return authService.login(loginRequest);
    }
}
