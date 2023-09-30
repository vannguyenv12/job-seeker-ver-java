package com.vannguyen.jobseeker.controller;

import com.vannguyen.jobseeker.payload.AuthResponse;
import com.vannguyen.jobseeker.payload.LoginDto;
import com.vannguyen.jobseeker.payload.UserDto;
import com.vannguyen.jobseeker.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@NoArgsConstructor
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody() UserDto userDto) {
        AuthResponse registerUser = userService.register(userDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody() LoginDto loginDto) {
        AuthResponse loggedUser = userService.login(loginDto);
        return new ResponseEntity<>(loggedUser, HttpStatus.CREATED);
    }
}
