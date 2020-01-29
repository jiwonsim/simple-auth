package com.jiwon.auth.membership.controller;

import com.jiwon.auth.membership.dto.JoinForm;
import com.jiwon.auth.membership.dto.LoginForm;
import com.jiwon.auth.membership.entity.User;
import com.jiwon.auth.membership.service.UserService;
import com.jiwon.auth.response.DefaultRes;
import com.jiwon.auth.response.ResponseMessage;
import com.jiwon.auth.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/join")
    public ResponseEntity joinUser(@RequestBody JoinForm joinForm) {
        try {
            if (hasUserId(joinForm.getUid()))
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.DUPLICATED_USER), HttpStatus.OK);

            User user = userService.createUser(joinForm.toUser());
            // 사용자 정보로 뭘할 수 있을까?
            return new ResponseEntity(DefaultRes.res(StatusCode.CREATE, ResponseMessage.SAVE_USER), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginForm loginForm) {
        if (!hasUserId(loginForm.getUid())) { // uid가 존재하지 않는 경우
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.NOT_FOUND_USER), HttpStatus.OK);
        }

        User user = userService.searchUser(loginForm);
        if (user == null) // password 입력 오류
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.INVALID_INFO), HttpStatus.OK);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user), HttpStatus.OK);
    }


    public boolean hasUserId(String uid) {
        User user = userService.getUserByUid(uid);
        if (user == null) return false;
        return true;
    }


}
