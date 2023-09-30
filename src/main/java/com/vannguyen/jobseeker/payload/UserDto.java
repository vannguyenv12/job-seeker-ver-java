package com.vannguyen.jobseeker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;

    private String firstName;
    private String lastName;
    private String password;

    private Set<String> skillList = new HashSet<>();
}
