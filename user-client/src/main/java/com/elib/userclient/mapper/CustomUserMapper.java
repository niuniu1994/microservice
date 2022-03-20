package com.elib.userclient.mapper;

import com.elib.userclient.dto.CustomUserDto;
import com.elib.userclient.dto.SignUpUserDto;
import com.elib.userclient.model.CustomUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface CustomUserMapper {

    @Mappings({
            @Mapping(target="email", source="accountInfo.email")
    })
    CustomUserDto customUser2CustomUserDto(CustomUser customUser);


    @Mappings({
            @Mapping(target="accountInfo.email", source="email"),
            @Mapping(target="accountInfo.password", source="password")
    })
    CustomUser signUpUserDto2CustomUser(SignUpUserDto signUpUserDto);
}
