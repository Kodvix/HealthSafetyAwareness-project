package com.kodvix.healthsafetyawareness.dto;


import com.kodvix.healthsafetyawareness.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String token;

    private Role role;

}