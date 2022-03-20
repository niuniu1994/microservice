package com.elib.bookclient.util;

import com.elib.bookclient.dto.CustomResponseEntity;
import org.springframework.http.HttpStatus;

/**
 * @author kainingxin
 */
public class ResponseEntityFactory {

    public static CustomResponseEntity oK(Object data){
        return new CustomResponseEntity(HttpStatus.OK.value(),"success",data);
    }
    public static CustomResponseEntity oK(String msg, Object data){
        return new CustomResponseEntity(HttpStatus.OK.value(),msg,data);
    }

    public static CustomResponseEntity noContent(Object data){
        return new CustomResponseEntity(HttpStatus.NO_CONTENT.value(), "No content found",data);
    }

    public static CustomResponseEntity error(Object data){
        return new CustomResponseEntity(HttpStatus.BAD_REQUEST.value(), "bad request",data);
    }

    public static CustomResponseEntity error(String msg, Object data){
        return new CustomResponseEntity(HttpStatus.BAD_REQUEST.value(), msg,data);
    }

    public static CustomResponseEntity unAuth(Object data){
        return new CustomResponseEntity(HttpStatus.UNAUTHORIZED.value(), "UnAuthorised operation",data);
    }
}
