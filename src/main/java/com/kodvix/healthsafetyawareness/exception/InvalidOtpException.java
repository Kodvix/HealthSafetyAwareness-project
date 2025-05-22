package com.kodvix.healthsafetyawareness.exception;


public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}