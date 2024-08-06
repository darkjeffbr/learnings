package com.darkjeff.cqrs.model.query;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserEntity {

    private Long id;

    private String name;
}
