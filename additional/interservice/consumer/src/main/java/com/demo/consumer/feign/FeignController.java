package com.demo.consumer.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feign") //LoadBalance include
public class FeignController {

    @Autowired
    private ProviderFeignClient providerFeignClient;

    @GetMapping("/instance")
    public String getInstance(){
        return providerFeignClient.getInstanceInfo();
    }

}
