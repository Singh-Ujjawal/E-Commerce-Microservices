package com.demo.consumer.restClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProviderRestClient {

    @Autowired
    private RestClient restClient;

    public String getInstance(){
        return restClient.get().uri("/instance-info")
                .retrieve()
                .body(String.class);
    }

}
