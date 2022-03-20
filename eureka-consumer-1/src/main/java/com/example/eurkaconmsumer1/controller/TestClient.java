package com.example.eurkaconmsumer1.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client")
public interface TestClient {

    @GetMapping("/test")
    String getTest();
}
