package com.ecommerce.notification;

import com.ecommerce.notification.payload.OrderEventCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Service
@Slf4j
public class OrderEventConsumer {

//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderEventCreated orderEvent) {
//        System.out.println("Received order event: " + orderEvent);
//        long orderId = Long.parseLong(orderEvent.getOrderId().toString());
//        String orderStatus = (String) orderEvent.getOrderStatus().toString();
//        System.out.println("Order ID: " + orderId + " Order Status: " + orderStatus);
//    }

    @Bean
    public Consumer<OrderEventCreated> orderCreated() {
        return event -> {
            log.info("Received order created event for order id : {}", event.getOrderId());
            log.info("Received order created event for user id : {}", event.getUserId());
        };
    }

}
