package com.jiwon.auth.membership.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GoogleUserResponse {
    private String id;
    private String email;
    private Boolean verified_email;
    private String picture;
}
