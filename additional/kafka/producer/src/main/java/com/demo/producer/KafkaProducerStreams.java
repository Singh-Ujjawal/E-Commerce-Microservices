package com.demo.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class KafkaProducerStreams {
    @Bean
    public Supplier<RiderLocation>  riderLocationSupplier() {
        return () -> {
            RiderLocation riderLocation = new RiderLocation("Rider123",12.34,45.67);
            System.out.println("Sending : " + riderLocation.getRiderId());
            return riderLocation;
        };
    }
}
