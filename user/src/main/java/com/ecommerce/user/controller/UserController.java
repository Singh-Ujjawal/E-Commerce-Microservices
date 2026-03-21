package com.ecommerce.user.controller;


import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

//    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
//        logger.info("Fetching user with id: " + id);
        log.info("Fetching user with id: {}", id);
        log.warn("Warn");
        log.error("Error");
        log.trace("Tracing");
        log.debug("Debug");
        return userService.getUser(id).
                map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<List<UserResponse>> createUser(@RequestBody UserRequest user) {
        userService.createUser(user);
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest user) {
         if(userService.updateUser(id, user)) return ResponseEntity.ok("User updated successfully");
         return ResponseEntity.notFound().build();
    }
}
