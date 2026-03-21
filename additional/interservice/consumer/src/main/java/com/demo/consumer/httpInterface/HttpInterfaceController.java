package com.demo.consumer.httpInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/http-interface")
public class HttpInterfaceController {

    @Autowired
    private ProviderHttpInterface service;

    @GetMapping
    public String getInstanceInfo(){
        return service.getInstanceInfo();
    }

}
