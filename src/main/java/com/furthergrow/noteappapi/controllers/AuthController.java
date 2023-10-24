package com.furthergrow.noteappapi.controllers;

import com.furthergrow.noteappapi.entity.User;
import com.furthergrow.noteappapi.interfaces.AuthenticationServiceImpl;
import com.furthergrow.noteappapi.interfaces.UserServiceImpl;
import com.furthergrow.noteappapi.models.SignInRequest;
import com.furthergrow.noteappapi.models.SignUpRequest;
import com.furthergrow.noteappapi.models.TokenRequest;
import com.furthergrow.noteappapi.services.AuthenticationService;
import com.furthergrow.noteappapi.services.UserService;
import com.furthergrow.noteappapi.utils.JwtTokenUtil;
import eu.fraho.spring.securityJwt.dto.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public Object signUpUser(@RequestBody SignUpRequest user){
        return  authenticationService.signup(user);
    }
    @PostMapping("/login")
    public Object signInUser(@RequestBody SignInRequest user){
        return  authenticationService.signin(user);
    }
    @PostMapping("/refreshToken")
    public Object refreshToken(@RequestBody TokenRequest tokenRequest){
        return  authenticationService.refreshToken(tokenRequest);
    }
}
