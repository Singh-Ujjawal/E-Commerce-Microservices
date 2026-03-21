package com.demo.producer;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class KafkaProducer {
//
//    @Autowired
//    private KafkaTemplate<String, RiderLocation> kafkaTemplate;
//
//    @PostMapping
//    private String sendMessage(@RequestParam String message) {
//        RiderLocation riderLocation = new RiderLocation("Rider123",12.34,23.34);
//        kafkaTemplate.send("my-topic-4", riderLocation);
//        return "Message sent: " + riderLocation.getRiderId();
//    }
//}
