package com.furthergrow.noteappapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    public String email;
    public String password;
}
