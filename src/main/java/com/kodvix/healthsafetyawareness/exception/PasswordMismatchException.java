package com.kodvix.healthsafetyawareness.exception;


public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}