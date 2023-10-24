package com.furthergrow.noteappapi.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Object handleSecurityException(Exception exception) {
        Map<String,Object> map=new HashMap<>();
        if (exception instanceof BadCredentialsException) {
            map.put("description",ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage()));
            map.put("message", "The username or password is incorrect");
            return map;
        }

        if (exception instanceof AccountStatusException) {
            map.put("description",ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage()));
            map.put("message", "The account is locked");
            return map;
        }

        if (exception instanceof AccessDeniedException) {
            map.put("description",ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage()));
            map.put("message","You are not authorized to access this resource");
        }


        if (exception instanceof ExpiredJwtException) {
            map.put("description",ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage()));
            map.put("message","The JWT token has expired");
        }

        if (!map.containsKey("message")) {
            map.put("description",ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage()));
            map.put("message","Unknown internal server error.");
        }
        map.put("error",true);
        return map;
    }
}
