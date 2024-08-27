package org.personal.user_service.user.response;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;

public record ResponseUserDetail(
        // 사용자의 모든 정보
         Long userId,
         String userEmail,
         String userNickname,
         String userPassword,
         String userEnrollDate,
         String userDeleteDate,
         boolean userIsDeleted,
         ROLE userRole
) {
    public ResponseUserDetail(User user) {
        this(
                user.getUserId(),
                user.getUserEmail(),
                user.getUserNickname(),
                user.getUserPassword(),
                DateParsing.LdtToStr(user.getUserEnrollDate()),
                DateParsing.LdtToStr(user.getUserDeleteDate()),
                user.isUserIsDeleted(),
                user.getUserRole()
        );
    }
}
