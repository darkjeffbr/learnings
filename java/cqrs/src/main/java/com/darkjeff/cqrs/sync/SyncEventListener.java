package com.darkjeff.cqrs.sync;

import com.darkjeff.cqrs.model.command.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncEventListener implements ApplicationListener<SyncEvent> {

    private final UserRepository commandUserRepository;
    private final com.darkjeff.cqrs.model.query.UserRepository queryUserRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void onApplicationEvent(SyncEvent event) {
        queryUserRepository.storeAll(userEntityMapper.toQueryModel(commandUserRepository.getAll()));
    }
}