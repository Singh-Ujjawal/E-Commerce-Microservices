package com.ecommerce.order.controller;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.Cartitem;
import com.ecommerce.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-USER-ID") String userId,
                                          @RequestBody CartItemRequest cartItemRequest) {
        Integer res = cartService.addToCart(userId.toString(),cartItemRequest);
        if(res==1) {
            return ResponseEntity.badRequest().body("Product out of stock or Product not found");
        }
        else if(res==2) {
            return ResponseEntity.badRequest().body("User not found");
        }
        else if(res==3) {
            return ResponseEntity.badRequest().body("User Service / Product Service not Available");
        }
        return ResponseEntity.ok().body("Product added to cart successfully");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> deleteCart(@RequestHeader("X-USER-ID") String userId,@PathVariable Long productId) {
        boolean res = cartService.deleteCart(userId.toString(),productId.toString());
        return res ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Cartitem>> getCartItems(@RequestHeader("X-USER-ID") String userId) {
        return new ResponseEntity<>(cartService.getCartByUserId(userId.toString()),HttpStatus.OK);
    }
}
