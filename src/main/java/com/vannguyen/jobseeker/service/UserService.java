package com.vannguyen.jobseeker.service;

import com.vannguyen.jobseeker.entity.User;
import com.vannguyen.jobseeker.payload.AuthResponse;
import com.vannguyen.jobseeker.payload.LoginDto;
import com.vannguyen.jobseeker.payload.UserDto;

public interface UserService {
    AuthResponse register(UserDto userDto);

    AuthResponse login(LoginDto loginDto);

    User getLoggedUser();
}
