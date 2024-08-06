package com.darkjeff.cqrs.controller;

import com.darkjeff.cqrs.handler.command.CreateUserCommand;
import com.darkjeff.cqrs.handler.command.UserCommandHandler;
import com.darkjeff.cqrs.handler.query.UserDTO;
import com.darkjeff.cqrs.handler.query.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandHandler userCommandHandler;
    private final UserQueryHandler userQueryHandler;

    @GetMapping(path = "{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        var user = userQueryHandler.getUser(userId);
        if(Objects.isNull(user)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserCommand createUser) {
        var userId = userCommandHandler.handle(createUser);
        var uri =  ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(userId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
