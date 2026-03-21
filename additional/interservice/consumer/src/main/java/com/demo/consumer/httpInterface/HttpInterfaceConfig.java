package com.demo.consumer.httpInterface;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {

//    @Bean
//    @LoadBalanced
//    public WebClient.Builder loadBalancedWebClientBuilder() {
//        return WebClient.builder();
//    }

    @Bean
    public ProviderHttpInterface webClientHttpInterface(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl("http://provider").build();
        WebClientAdapter webClientAdapter =  WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        ProviderHttpInterface service = factory.createClient(ProviderHttpInterface.class);
        return service;
    }



//    @Bean
//    @LoadBalanced
//    public RestClient.Builder getRestClientBuilder() {
//        return RestClient.builder();
//    }

//    @Bean
//    public ProviderHttpInterface restClientHttpInterface() {
//        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8081").build();
//        RestClientAdapter restClientAdapter =  RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
//        ProviderHttpInterface service = factory.createClient(ProviderHttpInterface.class);
//        return service;
//    }

    //Same for RestTemplate
//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

}
