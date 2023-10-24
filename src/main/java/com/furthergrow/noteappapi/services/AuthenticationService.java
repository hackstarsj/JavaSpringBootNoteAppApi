package com.furthergrow.noteappapi.services;

import com.furthergrow.noteappapi.models.SignInRequest;
import com.furthergrow.noteappapi.models.SignUpRequest;
import com.furthergrow.noteappapi.models.TokenRequest;
import eu.fraho.spring.securityJwt.dto.JwtAuthenticationResponse;

public interface AuthenticationService {
    Object signup(SignUpRequest request);
    Object refreshToken(TokenRequest tokenRequest);
    Object signin(SignInRequest request);
}