package com.elib.userclient.controller;

import com.elib.userclient.dto.*;
import com.elib.userclient.service.CustomUserService;
import com.elib.userclient.util.ResponseEntityFactory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class CustomUserController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    CustomUserService customUserService;

    @GetMapping("/test")
    public String test()  {
        return "Services" + discoveryClient.getServices();
    }

    @PostMapping("/user/signup")
    public CustomResponseEntity signUp(@RequestBody @NotNull SignUpUserDto signUpUserDto) {
        if ( customUserService.addUser(signUpUserDto)) {
            return ResponseEntityFactory.oK(null);
        }
        return ResponseEntityFactory.error(null);
    }

    @PostMapping("/user/login")
    public CustomResponseEntity login(@RequestBody @Valid LoginUserDto loginUserDto){
        Map<String, String> res = customUserService.login(loginUserDto);
        if (res != null){
            return ResponseEntityFactory.oK(res);
        }
        return  ResponseEntityFactory.error(null);
    }

    @GetMapping("/user/{email}/complete")
    public CustomResponseEntity retrieveUserCompleteInfo(@PathVariable @NonNull String email){
        return ResponseEntityFactory.oK(customUserService.findUserCompleteInfoByEmail(email));
    }
    @PostMapping("/user/book")
    public CustomResponseEntity borrowBook(@RequestBody @NonNull BookUserId bookUserId){
        if (customUserService.borrowBook(bookUserId)){
            return ResponseEntityFactory.oK(null);
        }
        return ResponseEntityFactory.error("Can't borrow the book",null);
    }


    @PatchMapping ("/user/book")
    public CustomResponseEntity returnBook(@RequestBody @NonNull BookUserId bookUserId){
        if (customUserService.returnBook(bookUserId)){
            return ResponseEntityFactory.oK(null);
        }
        return ResponseEntityFactory.error("Can't return the book",null);
    }


    @GetMapping("/user/name/{email}")
    public CustomResponseEntity findUserByEmail(@PathVariable String email){
        AuthUserDto res = customUserService.loadUserByUsername(email);
        if (res != null){
            return ResponseEntityFactory.oK(res);
        }
        return  ResponseEntityFactory.error(null);
    }

}
