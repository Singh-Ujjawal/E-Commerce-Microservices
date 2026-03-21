package com.demo.consumer;

//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaConsumer {
//
//    @KafkaListener(topics = "my-topic" , groupId = "my-new-group")
//    public void listen1(String message) {
//        System.out.println("kafka consumer receive message 1 : " + message);
//    }
//
//    @KafkaListener(topics = "my-topic" , groupId = "my-new-group")
//    public void listen2(String message) {
//        System.out.println("kafka consumer receive message 2 : " + message);
//    }
//
//    @KafkaListener(topics = "my-topic-4" , groupId = "my-new-group")
//    public void listenRider(RiderLocation riderLocation) {
//        System.out.println("Received Location : " + riderLocation.getRiderId() +
//                " " +  riderLocation.getLatitude() + " " +  riderLocation.getLongitude());
//    }
//}
