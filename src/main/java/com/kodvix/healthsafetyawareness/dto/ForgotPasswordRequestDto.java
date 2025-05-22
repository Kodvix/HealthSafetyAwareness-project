package com.kodvix.healthsafetyawareness.dto;




import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
 
public class ForgotPasswordRequestDto {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Repeat Email is required")
    private String repeatEmail;
}