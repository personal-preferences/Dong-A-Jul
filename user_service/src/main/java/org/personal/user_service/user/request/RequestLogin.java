package org.personal.user_service.user.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestLogin (
        @NotEmpty
        String userEmail,
        @NotEmpty
        String userPassword
)
{
}
