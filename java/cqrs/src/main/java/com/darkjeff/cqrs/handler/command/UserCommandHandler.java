package com.darkjeff.cqrs.handler.command;

import com.darkjeff.cqrs.model.command.UserEntity;
import com.darkjeff.cqrs.model.command.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandHandler {

    private final UserRepository userRepository;

    public Long handle(CreateUserCommand command) {
        var user = UserEntity.builder().name(command.name()).build();
        var createdUser = userRepository.save(user);
        return createdUser.getId();
    }
}
