package org.personal.user_service.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.personal.user_service.user.etc.ROLE;


public record RequestRegist(
        @NotEmpty(message = "이메일 누락")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.+[A-Za-z]{2,6}$", message = "이메일 형식이 맞지 않습니다.")
        String userEmail,
        @NotEmpty(message = "닉네임 누락")
        @Size(min = 2,max = 15, message = "2자 이상, 20글자 이하의 닉네임만 가능합니다.")
        String userNickname,
        @NotEmpty(message = "비밀번호 누락")
        @Size(min = 8,max = 15, message = "8자 이상, 15글자 이하의 비밀번호만 가능합니다.")
        String userPassword
//        ,
//        @NotEmpty(message = "역할 누락")
//        @Pattern(regexp = "ROLE_USER|ROLE_ADMIN", message = "역할은 ROLE_USER 또는 ROLE_ADMIN만 가능합니다.")
//        String userRole
)
{

}
