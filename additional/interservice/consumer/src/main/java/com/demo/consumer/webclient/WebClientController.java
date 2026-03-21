package com.demo.consumer.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/web-client")
public class WebClientController {

    @Autowired
    private ProviderWebClient providerWebClient;

    @GetMapping("/instance")
    public Mono<String> instance() {
        return providerWebClient.instance();
    }
}
