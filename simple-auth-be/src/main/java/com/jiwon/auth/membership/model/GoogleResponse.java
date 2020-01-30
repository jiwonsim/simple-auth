package com.jiwon.auth.membership.model;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GoogleResponse {
    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
