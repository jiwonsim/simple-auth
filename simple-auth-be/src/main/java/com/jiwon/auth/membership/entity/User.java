package com.jiwon.auth.membership.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long num;

    private String name;
    private String uid;
    private String password;

    @Builder
    public User(String name, String uid, String password) {
        this.name = name;
        this.uid = uid;
        this.password = password;
    }

}
