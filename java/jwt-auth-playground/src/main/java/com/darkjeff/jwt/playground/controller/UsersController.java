package com.darkjeff.jwt.playground.controller;

import com.darkjeff.jwt.playground.model.entity.User;
import com.darkjeff.jwt.playground.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

  private final UserRepository userRepository;

  @GetMapping
  public ResponseEntity<List<User>> listUsers() {
    return ResponseEntity.ok(userRepository.findAll());
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User newUser) {
    User createdUser = userRepository.save(newUser);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(createdUser);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(
      @PathVariable("id") Long id, @RequestBody User toUpdateUser) {
    Optional<User> user = userRepository.findById(id);
    return user.map(
            u -> {
              u.updateFrom(toUpdateUser);
              userRepository.save(u);
              return ResponseEntity.ok(u);
            })
        .orElse(ResponseEntity.notFound().build());
  }
}
