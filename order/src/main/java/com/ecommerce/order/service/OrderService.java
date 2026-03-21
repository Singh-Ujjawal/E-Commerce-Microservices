package com.ecommerce.order.service;


import com.ecommerce.order.dto.OrderEventCreated;
import com.ecommerce.order.model.Cartitem;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;

    @Autowired
    private StreamBridge streamBridge;


//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    public Optional<OrderResponse> createOrder(String userId) {
//        User user = userRepository.findById(Long.valueOf(userId)).orElse(null);
//        if(user == null){
//            return Optional.empty();
//        }

        List<Cartitem> cartitems = cartService.getCartByUserId(userId);
        if(cartitems == null || cartitems.isEmpty()){
            return Optional.empty();
        }

        BigDecimal totalPrice = cartitems.stream().map(Cartitem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        //Id,Product,Quantity,Price,Order
        List<OrderItem> orderItems = cartitems.stream()
                .map(item->new OrderItem(null,item.getProductId(),item.getQuantity(),item.getPrice(),order))
                .toList();

        order.setItems(orderItems);

        Order saved = orderRepository.save(order);

        cartService.clearCart(userId);
//      Publish Order Event
        OrderEventCreated orderEventCreated = new OrderEventCreated(
                saved.getId(),
                saved.getUserId(),
                saved.getOrderStatus(),
                saved.getTotalAmount(),
                mapToOrderItemDTOs(saved.getItems()),
                saved.getCreatedAt()
        );

//        rabbitTemplate.convertAndSend("order.exchange",

        streamBridge.send("createOrder-out-0", orderEventCreated);

        return Optional.of(mapToOrderResponse(saved));
    }

    private List<OrderItemDTO> mapToOrderItemDTOs(List<OrderItem> items) {
        return items.stream().map(it-> new OrderItemDTO(
                it.getId(),
                it.getProductId(),
                it.getQuantity(),
                it.getPrice(),
                it.getPrice().multiply(new BigDecimal(it.getQuantity()))
        )).collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(Order saved) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(saved.getId());
        orderResponse.setPrice(saved.getTotalAmount());
        orderResponse.setStatus(saved.getOrderStatus());
        //Id,PId,Quant,Price,Subtotal
        orderResponse.setOrderItems(saved.getItems().stream().
                map(item->new OrderItemDTO(
                        item.getId(),
                        item.getProductId(),
                        item.getQuantity(),
                        BigDecimal.valueOf(1000.00),
                        BigDecimal.valueOf(1000.00)
                )).toList());
        orderResponse.setCreatedAt(saved.getCreatedAt());
        return orderResponse;
    }
}
