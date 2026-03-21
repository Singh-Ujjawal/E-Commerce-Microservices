package com.ecommerce.order.clients;

import com.ecommerce.order.dto.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
@Service
public interface UserServiceClient {

    @GetExchange("/api/users/{id}")
    UserResponse getUserDetails(@PathVariable String id);

}
