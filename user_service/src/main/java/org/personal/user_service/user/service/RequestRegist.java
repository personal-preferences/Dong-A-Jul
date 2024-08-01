package org.personal.user_service.user.service;

import org.personal.user_service.user.etc.ROLE;

public record RequestRegist(
        String userEmail,
        String userNickname,
        String userPassword,
        ROLE userRole
)
{

}
