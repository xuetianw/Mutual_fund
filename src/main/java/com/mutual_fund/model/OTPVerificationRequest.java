package com.mutual_fund.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OTPVerificationRequest {
    private String otp;
    private String email;
    private String password;
}
