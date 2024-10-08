package org.personal.user_service.user.repository;

import org.personal.user_service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserEmail(String userEmail);

    boolean existsByUserNickname(String userNickname);

    User findByUserEmail(String userEmail);

    boolean existsByUserEmailOrUserNickname(String s, String s1);

    User findByUserEmailOrUserNickname(String s, String s1);
}
