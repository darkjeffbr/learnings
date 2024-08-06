package com.darkjeff.cqrs.handler.query;

import com.darkjeff.cqrs.model.query.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "name", source = "name")
    UserDTO toDTO(UserEntity user);
}
