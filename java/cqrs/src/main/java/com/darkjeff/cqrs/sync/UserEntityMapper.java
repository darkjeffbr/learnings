package com.darkjeff.cqrs.sync;

import com.darkjeff.cqrs.model.command.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {
    List<com.darkjeff.cqrs.model.query.UserEntity> toQueryModel(List<UserEntity> toMap);

}
