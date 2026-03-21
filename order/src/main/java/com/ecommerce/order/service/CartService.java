package com.ecommerce.order.service;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.model.Cartitem;
import com.ecommerce.order.repository.CartItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    int attempts = 0;

//    @CircuitBreaker(name = "productService",fallbackMethod = "addToCartFallBack")
@Retry(name = "retryBreaker",fallbackMethod = "addToCartFallBack")
    public Integer addToCart(String userId, CartItemRequest cartItemRequest) {
        System.out.println("Attempt to add to cart " + ++attempts);
        ProductResponse productResponse = productServiceClient.getProductDetails(cartItemRequest.getProductId().toString());

        if(productResponse==null || productResponse.getStockQuantity() < cartItemRequest.getQuantity()) return 1;

        UserResponse userResponse = userServiceClient.getUserDetails(userId);
        if(userResponse==null) return 2;

        Cartitem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId,cartItemRequest.getProductId().toString());
        if(existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
//            BigDecimal x = product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity()));
//            product.setStockQuantity(product.getStockQuantity() - cartItemRequest.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
//            productRepository.save(product);
            cartItemRepository.save(existingCartItem);
        }

        else {
            Cartitem newCartItem = new Cartitem();
            newCartItem.setUserId(userId);
            newCartItem.setProductId(cartItemRequest.getProductId().toString());
            newCartItem.setQuantity(cartItemRequest.getQuantity());
            newCartItem.setPrice(BigDecimal.valueOf(1000.00));
//            product.setStockQuantity(product.getStockQuantity() - cartItemRequest.getQuantity());
//            productRepository.save(product);
            cartItemRepository.save(newCartItem);
        }
        return 4;
    }


    public Integer addToCartFallBack(String userId, CartItemRequest cartItemRequest,Exception e) {
        System.out.println("FallBack Called");
        return 3;
    }

    public boolean deleteCart(String userId, String productId) {
//        Optional<Product> productOpt = productRepository.findById(productId);
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        Cartitem cartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);
        if(cartItem != null) {
            cartItemRepository.deleteByUserIdAndProductId(userId,productId);
            return  true;
        }
        return false;
    }

    public List<Cartitem> getCartByUserId(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
