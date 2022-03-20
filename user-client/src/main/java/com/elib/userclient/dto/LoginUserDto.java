package com.elib.userclient.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class LoginUserDto {
    @NotNull
    String email;
    @NotNull
    String password;
}
