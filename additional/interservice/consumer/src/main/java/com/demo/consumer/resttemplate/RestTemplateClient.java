package com.demo.consumer.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateClient {

//    private static final String PROVIDER_URL = "http://localhost:8081";

    private static final String PROVIDER_URL = "http://provider";
    @Autowired
    private RestTemplate restTemplate;

    public String getInstanceInfo() {
        return restTemplate.getForObject(PROVIDER_URL + "/instance-info",String.class);
    }
}
