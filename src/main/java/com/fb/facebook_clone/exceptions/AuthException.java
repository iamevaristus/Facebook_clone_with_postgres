package com.fb.facebook_clone.exceptions;

public class AuthException extends Exception{
    public AuthException(String message) {
        super("AuthException: %s".formatted(message));
    }
}