package com.vannguyen.jobseeker.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
