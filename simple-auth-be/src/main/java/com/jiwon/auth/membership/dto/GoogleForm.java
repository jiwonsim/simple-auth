package com.jiwon.auth.membership.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GoogleForm {
    private String code;
    private String state;
    private String scope;
    private String authuser;
    private String prompt;
    private String session_state;
}
