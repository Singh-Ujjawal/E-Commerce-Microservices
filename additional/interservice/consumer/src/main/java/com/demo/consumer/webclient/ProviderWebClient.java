package com.demo.consumer.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProviderWebClient {

    @Autowired
    private WebClient webClient;

    public Mono<String> instance() {
        return webClient.get().uri("/instance-info").retrieve().bodyToMono(String.class);
    }

}
