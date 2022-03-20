package com.elib.userclient.dto;

/**
 * @author kainingxin
 */

import com.elib.userclient.model.Address;
import lombok.Value;

import java.util.List;


@Value
public class CustomUserDto {
    String firstName;
    String lastName;
    String email;
    Address address;
    List<Integer> books;
}
