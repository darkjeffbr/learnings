package com.darkjeff.jwt.playground.controller;

import com.darkjeff.jwt.playground.dto.UserLoginDetails;
import com.darkjeff.jwt.playground.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody UserLoginDetails userLoginDetails) {
        return ResponseEntity.ok(authenticationService.authenticate(userLoginDetails));
    }

}
