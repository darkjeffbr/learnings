package com.darkjeff.jwt.playground.controller;

import com.darkjeff.jwt.playground.dto.UserLoginDetails;
import com.darkjeff.jwt.playground.model.repository.UserRepository;
import com.darkjeff.jwt.playground.service.AuthenticationService;
import com.darkjeff.jwt.playground.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody UserLoginDetails userLoginDetails) {
        return ResponseEntity.ok(authenticationService.authenticate(userLoginDetails));
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateToken() {
        return ResponseEntity.ok(jwtService.generateToken(userRepository.findByName("user").get()));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestBody String token) {
        return ResponseEntity.ok(jwtService.validateToken(token));
    }

}
