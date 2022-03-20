package com.elib.userclient.dto;

import com.elib.userclient.model.Address;
import lombok.Value;

@Value
public class SignUpUserDto {
    String email;
    String firstName;
    String lastName;
    String password;
    Address address;
}
