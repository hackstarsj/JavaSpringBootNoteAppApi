package com.furthergrow.noteappapi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {
    public String username;
    public String password;
    public String email;
}
