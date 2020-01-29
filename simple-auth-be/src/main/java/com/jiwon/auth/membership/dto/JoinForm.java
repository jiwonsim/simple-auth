package com.jiwon.auth.membership.dto;

import com.jiwon.auth.membership.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinForm {
    private String name;
    private String uid;
    private String password;

    public User toUser() {
        return User.builder()
                .name(name)
                .uid(uid)
                .password(password)
                .build();
    }
}
