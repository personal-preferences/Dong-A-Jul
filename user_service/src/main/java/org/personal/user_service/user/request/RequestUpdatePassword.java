package org.personal.user_service.user.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestUpdatePassword (

        @NotNull(message = "잘못된 userId입니다.")
        Long userId,

        @NotEmpty
        @Size(min = 8,max = 15, message = "8자 이상, 15글자 이하의 비밀번호만 가능합니다.")
        String userPassword
)
{

}
