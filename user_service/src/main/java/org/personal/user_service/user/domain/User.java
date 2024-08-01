package org.personal.user_service.user.domain;


import jakarta.persistence.*;
import lombok.*;
import org.personal.user_service.user.etc.ROLE;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_nickname")
    private String userNickname;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_enroll_date")
    private LocalDateTime userEnrollDate;
    @Column(name = "user_delete_date")
    private LocalDateTime userDeleteDate;
    @Column(name = "user_is_deleted")
    private boolean userIsDeleted;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private ROLE userRole;
}
