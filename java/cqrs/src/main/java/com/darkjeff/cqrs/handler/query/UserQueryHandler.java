package com.darkjeff.cqrs.handler.query;

import com.darkjeff.cqrs.model.query.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryHandler {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO getUser(Long userId) {
        var user = userRepository.findById(userId).orElse(null);
        return userMapper.toDTO(user);
    }

}
