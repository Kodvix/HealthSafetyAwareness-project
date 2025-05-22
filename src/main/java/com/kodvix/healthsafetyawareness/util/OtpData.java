package com.kodvix.healthsafetyawareness.util;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OtpData {
    private String otp;
    private LocalDateTime requestedTime;
}
