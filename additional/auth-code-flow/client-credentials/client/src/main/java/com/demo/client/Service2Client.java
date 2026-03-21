package com.demo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Service2Client {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    @Value("${service2.url}")
    String url;

    public String fetchData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String incomingToken = null;
        if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
           incomingToken =  jwtAuthenticationToken.getToken().getTokenValue();
        }
        System.out.println("incoming token: " + incomingToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(incomingToken);

        var resp = restTemplate.exchange(url + "/data", HttpMethod.GET, new HttpEntity<>(headers), String.class);

        return resp.getBody();
    }

}
