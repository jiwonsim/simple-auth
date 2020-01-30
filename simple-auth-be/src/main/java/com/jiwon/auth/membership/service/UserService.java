package com.jiwon.auth.membership.service;

import com.jiwon.auth.jwt.JwtService;
import com.jiwon.auth.membership.dto.LoginForm;
import com.jiwon.auth.membership.entity.ValidToken;
import com.jiwon.auth.membership.entity.User;
import com.jiwon.auth.membership.model.UserResponse;
import com.jiwon.auth.membership.repository.TokenRepository;
import com.jiwon.auth.membership.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    JwtService jwtService;

    public List<User> getAllUserList() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("INVALID_USER_DATA"));
        return user;
    }

    public JwtService.TokenRes issueToken(long num) {
        JwtService.TokenRes tokenRes = new JwtService.TokenRes(jwtService.create(num));
        ValidToken issuedValidToken = new ValidToken(tokenRes.getToken());
        tokenRepository.save(issuedValidToken);

        return tokenRes;
    }

    public UserResponse decodeTokenToUser(String token) {
        JwtService.Token decodedToken = jwtService.decode(token);
        Long num = decodedToken.getNum();

        User user = getUser(num);
        UserResponse response = new UserResponse(user.getUid(), user.getName());
        return response;
    }

    public void logoffUser(String inputToken) {
        ValidToken validToken = tokenRepository.findById(inputToken).orElseThrow(() -> new NoSuchElementException("INVALID_TOKEN_DATA"));
        tokenRepository.delete(validToken);
    }

    public User getUserByUid(String uid) {
        User user = userRepository.findAllByUid(uid);
        return user;
    }

    @Transactional
    public User createUser(User user) {
        String pwd = user.getPassword();
        String encodePwd = new BCryptPasswordEncoder().encode(pwd);
        user.setPassword(encodePwd);
        User createdUser = userRepository.save(user);
        return createdUser;
    }

    public User searchUser(LoginForm loginForm) {
        User existingUser = getUserByUid(loginForm.getUid());

        if (passwordEncoder.matches(loginForm.getPassword(), existingUser.getPassword())) {
            // token 발급
            return existingUser;
        }
        else return null;
    }
}
