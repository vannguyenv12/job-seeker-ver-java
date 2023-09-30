package com.vannguyen.jobseeker.service.impl;

import com.vannguyen.jobseeker.entity.Role;
import com.vannguyen.jobseeker.entity.User;
import com.vannguyen.jobseeker.exception.BadRequestException;
import com.vannguyen.jobseeker.exception.NotFoundException;
import com.vannguyen.jobseeker.exception.UnauthorizeException;
import com.vannguyen.jobseeker.payload.AuthResponse;
import com.vannguyen.jobseeker.payload.LoginDto;
import com.vannguyen.jobseeker.payload.UserDto;
import com.vannguyen.jobseeker.repository.RoleRepository;
import com.vannguyen.jobseeker.repository.UserRepository;
import com.vannguyen.jobseeker.security.JwtProvider;
import com.vannguyen.jobseeker.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  ModelMapper modelMapper;
    private  UserRepository userRepository;

    private JwtProvider jwtProvider;

    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleRepository roleRepository, JwtProvider jwtProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new BadRequestException("Email already exist");
        }

        Role role = this.roleRepository.findByName("ROLE_USER").get();
        Set<Role> roles = new HashSet<>() {};
        roles.add(role);

        User user = modelMapper.map(userDto, User.class);
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getEmail(), userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String access_token = this.jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccess_token(access_token);

        return authResponse;
    }

    public AuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String access_token = this.jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccess_token(access_token);

        return authResponse;
    }

    @Override
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizeException();
        }

        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

}
