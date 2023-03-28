package com.darkjeff.jwt.playground.service;

import com.darkjeff.jwt.playground.dto.UserLoginDetails;
import com.darkjeff.jwt.playground.model.entity.User;
import com.darkjeff.jwt.playground.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public String authenticate(UserLoginDetails userLoginDetails) {
        Optional<User> user = userRepository.findByName(userLoginDetails.getUsername());
        return user.map(u -> "found").orElse("not found");
    }

}
