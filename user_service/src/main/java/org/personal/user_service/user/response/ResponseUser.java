package org.personal.user_service.user.response;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;

public record ResponseUser (String userEmail,
                            String userNickName,
                            String userEnrollDate,
                            String userDeleteDate,
                            boolean userIsDeleted,
                            ROLE userRole)
{
}
