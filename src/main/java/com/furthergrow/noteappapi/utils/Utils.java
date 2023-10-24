package com.furthergrow.noteappapi.utils;

import com.furthergrow.noteappapi.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static Object responseHandler(Object object){
        Map<String,Object> map=new HashMap<>();
        map.put("data",object);
        if(object==null){
            map.put("error",true);
            map.put("message","Data Not Found");
        }
        else{
            map.put("error",false);
            map.put("message","Data Found");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static User AuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
