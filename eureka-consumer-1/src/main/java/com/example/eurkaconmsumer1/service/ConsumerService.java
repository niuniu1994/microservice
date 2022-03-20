package com.example.eurkaconmsumer1.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @Retry(name = "backendA",fallbackMethod = "fallback")
    @CircuitBreaker(name="backendA")
    public String consume(){
        return "good";
    }

    public String fallback(RuntimeException e){
        return e.toString() + "dwdw";
    }
}
