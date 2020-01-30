package com.jiwon.auth.jwt.utils;

import com.jiwon.auth.jwt.JwtService;
import com.jiwon.auth.membership.entity.User;
import com.jiwon.auth.membership.repository.UserRepository;
import com.jiwon.auth.response.DefaultRes;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Slf4j
@Component
@Aspect
public class AuthAspect {

    private final static String AUTHORIZATION = "Authorization";


    private final static DefaultRes DEFAULT_RES = DefaultRes.builder().statusCode(401).responseMessage("인증 실패").build();
    private final static ResponseEntity<DefaultRes> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.UNAUTHORIZED);

    private final HttpServletRequest httpServletRequest;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public AuthAspect(final HttpServletRequest httpServletRequest, final UserRepository userRepository, final JwtService jwtService) {
        this.httpServletRequest = httpServletRequest;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Around("@annotation(com.jiwon.auth.jwt.utils.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);

        if (jwt == null) return RES_RESPONSE_ENTITY;

        final JwtService.Token token = jwtService.decode(jwt);

        if (token == null) {
            return RES_RESPONSE_ENTITY;
        }
        else {
            final User user = userRepository.findById(token.getNum()).orElseThrow(() -> new NoSuchElementException());
            if (user == null) return RES_RESPONSE_ENTITY;
            return pjp.proceed(pjp.getArgs());
        }
    }
}