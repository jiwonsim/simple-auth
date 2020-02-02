package com.jiwon.auth.membership.controller;

import com.jiwon.auth.jwt.JwtService;
import com.jiwon.auth.jwt.utils.Auth;
import com.jiwon.auth.membership.dto.GoogleForm;
import com.jiwon.auth.membership.dto.JoinForm;
import com.jiwon.auth.membership.dto.LoginForm;
import com.jiwon.auth.membership.entity.User;
import com.jiwon.auth.membership.model.UserResponse;
import com.jiwon.auth.membership.service.GoogleService;
import com.jiwon.auth.membership.service.UserService;
import com.jiwon.auth.response.DefaultRes;
import com.jiwon.auth.response.ResponseMessage;
import com.jiwon.auth.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    GoogleService googleService;

//    @Auth
    @PostMapping("/logoff")
    public ResponseEntity logoffUser(@RequestHeader("Authorization") final String token) {
        try {
            userService.logoffUser(token);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESSFUL_LOGOFF), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/google")
    public ResponseEntity loginByGoogle(@RequestBody GoogleForm googleForm) {
        String response = googleService.getGoogleToken(googleForm.getCode());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.GOOGLE_LOGIN, response), HttpStatus.OK);
    }

    @GetMapping("/google")
    public ResponseEntity getUserByGoogle(@RequestHeader("Authorization") final String token) {
        String response = googleService.getGoogleUserInfo(token);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, response), HttpStatus.OK);
    }

    @GetMapping("/service")
    public ResponseEntity usingService(@RequestHeader("Authorization") final String token) {

        UserResponse user = userService.decodeTokenToUser(token);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user), HttpStatus.OK);
    }

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

        // 토큰 발급
        JwtService.TokenRes token = userService.issueToken(user.getNum());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, token), HttpStatus.OK);
    }

    public boolean hasUserId(String uid) {
        User user = userService.getUserByUid(uid);
        if (user == null) return false;
        return true;
    }


}
