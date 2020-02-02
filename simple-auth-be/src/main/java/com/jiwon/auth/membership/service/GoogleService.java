package com.jiwon.auth.membership.service;

import com.jiwon.auth.membership.entity.ValidToken;
import com.jiwon.auth.membership.model.GoogleResponse;
import com.jiwon.auth.membership.model.GoogleUserResponse;
import com.jiwon.auth.membership.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class GoogleService {

    @Autowired
    private TokenRepository tokenRepository;

    @Value("${GOOGLE.CLIENTID}")
    private String CLIENTID;

    @Value("${GOOGLE.CLIENTSECRET}")
    private String SECRETKEY;

    private String redirectUri = "http://www.jiwonxdoori.tk/home";
//  private String redirectUri = "http://localhost:8627/home";

    public RestTemplate restTemplate = new RestTemplate();

    public String getGoogleToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("code", code);
        body.add("client_id", CLIENTID);
        body.add("client_secret", SECRETKEY);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        URI uri = URI.create("https://oauth2.googleapis.com/token");

        ResponseEntity<GoogleResponse> response = restTemplate.exchange(uri, HttpMethod.POST, request, GoogleResponse.class);
        String issuedToken = response.getBody().getAccess_token();
        ValidToken token = new ValidToken(issuedToken);

        tokenRepository.save(token);

        return issuedToken;
    }

    public String getGoogleUserInfo(String token) {
        HttpHeaders headers = new HttpHeaders();
        URI uri = URI.create("https://www.googleapis.com/oauth2/v2/userinfo");

        headers.add("Authorization", "Bearer " + token);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        ResponseEntity<GoogleUserResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request, GoogleUserResponse.class);
        String emailInfo = response.getBody().getEmail();

        return emailInfo;
    }

    public void logoff(String token) {
        ValidToken validToken = tokenRepository.findById(token).orElseThrow(() -> new NoSuchElementException("INVALID_TOKEN_DATA"));
        tokenRepository.delete(validToken);
    }
}
