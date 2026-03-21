package com.demo.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumerNew {
    @Bean
    public Consumer<RiderLocation> consumerFactory() {
        return location -> {
            System.out.println("Received Rider Location: " + location.getRiderId()
             + " @ " + location.getLatitude() + ", " + location.getLongitude());
        };
    }

}
