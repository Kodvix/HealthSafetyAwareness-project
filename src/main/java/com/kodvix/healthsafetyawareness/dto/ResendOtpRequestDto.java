package com.kodvix.healthsafetyawareness.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResendOtpRequestDto {

    @NotBlank(message = "Email is required")
  //  @Schema(description = "User's email address", example = "user@example.com")
    private String email;
}
