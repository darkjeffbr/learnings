package com.darkjeff.cqrs.model.query;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("queryUserRepository")
public class UserRepository {

    private static final Map<Long, UserEntity> USER_ENTITY_STORAGE = new HashMap<>();
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(USER_ENTITY_STORAGE.get(id));
    }

    public void storeAll(List<UserEntity> queryModel) {
        queryModel.forEach(userEntity -> {
            USER_ENTITY_STORAGE.put(userEntity.getId(), userEntity);
        });
    }
}
