package com.elib.gateway;

import com.elib.gateway.filter.UserAuthFilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Value("${client.user}")
    private String USER_CLIENT_URI;
    @Value("${client.book}")
    private String BOOK_CLIENT_URI;
    private final String USER_URI = "/api/user";
    private final String BOOK_URI = "/api/books";


    @Autowired
    UserAuthFilterFactory userAuthFilterFactory;


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("login", r -> r.order(0).path(USER_URI + "/login").filters(f ->f.retry(1)).uri(USER_CLIENT_URI))
                .route("signup", r -> r.order(1).path(USER_URI + "/signup").filters(f ->f.retry(1)).uri(USER_CLIENT_URI))
                .route("getUserInfo",r -> r.order(1).path(USER_URI + "/*/complete").filters(f ->f.filter(userAuthFilterFactory.apply(f))).uri(USER_CLIENT_URI))
                .route("authRouteBook", r -> r.order(5).method(HttpMethod.DELETE,HttpMethod.POST,HttpMethod.PATCH).and().path(BOOK_URI + "/**").filters(f ->f.filter(userAuthFilterFactory.apply(f))).uri(BOOK_CLIENT_URI))
                .route("bookGeneral", r -> r.order(10).path(BOOK_URI + "/**").filters(f ->f.retry(1)).uri(BOOK_CLIENT_URI))
                .route("userGeneral", r -> r.order(10).path(USER_URI + "/**").filters(f ->f.retry(1)).uri(USER_CLIENT_URI))
                .build();
    }



}
