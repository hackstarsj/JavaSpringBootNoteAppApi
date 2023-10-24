package com.furthergrow.noteappapi.interfaces;

import com.furthergrow.noteappapi.entity.User;
import com.furthergrow.noteappapi.models.Role;
import com.furthergrow.noteappapi.models.SignInRequest;
import com.furthergrow.noteappapi.models.SignUpRequest;
import com.furthergrow.noteappapi.models.TokenRequest;
import com.furthergrow.noteappapi.repository.UserRepository;
import com.furthergrow.noteappapi.services.AuthenticationService;
import com.furthergrow.noteappapi.services.JwtService;
import eu.fraho.spring.securityJwt.dto.AccessToken;
import eu.fraho.spring.securityJwt.dto.JwtAuthenticationResponse;
import eu.fraho.spring.securityJwt.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.expirationRefresh}")
    private String expirationRefresh;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public Object signup(SignUpRequest request) {

        var user = User.builder().username(request.getUsername()).email(request.getEmail())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).userType(Role.USER.toString())
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user,user);
        var refreshToken = jwtService.generateRefreshToken(user);
        AccessToken accessToken=new AccessToken(jwt,Integer.parseInt(expiration));
        RefreshToken refreshToken1=new RefreshToken(refreshToken, Integer.parseInt(expirationRefresh),null);
        return new JwtAuthenticationResponse(accessToken,refreshToken1);
    }

    @Override
    public Object refreshToken(TokenRequest tokenRequest) {

        String email=jwtService.extractUserEmail(tokenRequest.getRefreshToken());
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user, user);
        AccessToken accessToken = new AccessToken(jwt, Integer.parseInt(expiration));
        var refreshToken = jwtService.generateRefreshToken(user);
        RefreshToken refreshToken1=new RefreshToken(refreshToken,  Integer.parseInt(expirationRefresh),null);

        return new JwtAuthenticationResponse(accessToken, refreshToken1);
    }

    @Override
    public Object signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        try {
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
            var jwt = jwtService.generateToken(user, user);
            AccessToken accessToken = new AccessToken(jwt, Integer.parseInt(expiration));
            var refreshToken = jwtService.generateRefreshToken(user);
            RefreshToken refreshToken1=new RefreshToken(refreshToken,  Integer.parseInt(expirationRefresh),null);

            return new JwtAuthenticationResponse(accessToken, refreshToken1);
        }
        catch (Exception e){
            Map<String,Object> error=new HashMap<>();
            error.put("error",true);
            error.put("message","Invalid Login Details");
            return error;

        }
    }
}