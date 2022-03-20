package com.example.eurkaconmsumer1.controller;

import com.example.eurkaconmsumer1.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyController  {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/consumer")
    public String consumer(){
//        return testClient.getTest();
       return consumerService.consume();
    }
}
