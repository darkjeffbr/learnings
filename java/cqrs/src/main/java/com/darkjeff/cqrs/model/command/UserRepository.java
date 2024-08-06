package com.darkjeff.cqrs.model.command;


import com.darkjeff.cqrs.sync.SyncEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("commandUserRepository")
@RequiredArgsConstructor
public class UserRepository {

    private final ApplicationEventPublisher applicationEventPublisher;
    private static final Map<Long, UserEntity> USER_ENTITY_STORAGE = new HashMap<>();
    public UserEntity save(UserEntity user) {
        user.setId(generateNextId());
        USER_ENTITY_STORAGE.put(user.getId(), user);
        applicationEventPublisher.publishEvent(new SyncEvent());
        return user;
    }

    private Long generateNextId() {
        return Long.valueOf(USER_ENTITY_STORAGE.size()) + 1;
    }

    public List<UserEntity> getAll() {
        return USER_ENTITY_STORAGE.values().stream().toList();
    }
}
