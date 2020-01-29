package com.jiwon.auth.membership.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginForm {
    private String uid;
    private String password;
}
