package com.demo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ProxyController {

    @Autowired
    private Service2Client service2Client;

    @GetMapping("/proxy")
    public String getData() {
        return service2Client.fetchData();
    }

}
