package com.darkjeff.cqrs.handler.command;

import lombok.NonNull;

public record CreateUserCommand(
        @NonNull String name
) {
}
