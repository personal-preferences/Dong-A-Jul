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
    public ResponseUser(User user) {
        this(
                user.getUserEmail(),
                user.getUserNickname(),
                DateParsing.LdtToStr(user.getUserEnrollDate()),
                DateParsing.LdtToStr(user.getUserDeleteDate()),
                user.isUserIsDeleted(),
                user.getUserRole()
        );
    }
}
