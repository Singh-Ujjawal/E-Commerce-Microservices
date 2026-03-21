package com.demo.consumer.restClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/rest-client")
public class RestClientController {

    @Autowired
    private ProviderRestClient providerRestClient;

    @GetMapping
    public String getInstance() {
        return providerRestClient.getInstance();
    }

}
