package com.elib.gateway.filter;

import com.elib.gateway.dto.AuthUserDto;
import com.elib.gateway.util.JwtUtil;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @author kainingxin
 */
@Component
public class UserAuthFilterFactory implements GatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (isValidRequest(request)) {
                return chain.filter(exchange);
            } else {
                ServerHttpResponse response =  exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                byte[] bytes = "UnAuthorised".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return  response.writeWith(Flux.just(buffer));
            }

        };
    }

    private boolean isValidRequest(ServerHttpRequest request) {
        HttpHeaders httpHeaders = request.getHeaders();
        String header = httpHeaders.getFirst("Authorization");
        if (header == null || ! header.startsWith("Bearer ")) {
            return false;
        }
        String token = header.split(" ")[1];
        AuthUserDto authUserDto = JwtUtil.getUser(token);
        if (authUserDto != null && (authUserDto.getAuth().equals("admin") || authUserDto.getAuth().equals("user") )){
            return true;
        }
        return false;
    }
}
