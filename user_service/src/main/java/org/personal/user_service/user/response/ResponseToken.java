package org.personal.user_service.user.response;

public record ResponseToken(
        String accessToken,
        String refreshToken
)
{
}
