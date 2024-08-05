package org.personal.user_service.user.domain;


import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@RedisHash("refresh_token")
@ToString
public class Token {
    @Id // 주의 redis ID 랑 JPA ID랑 다르다
    String userNickname;
    @Indexed
    String token;
    @TimeToLive // 만료시간 (초단위)
    private long ttl;
}
