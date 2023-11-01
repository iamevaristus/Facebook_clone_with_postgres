package com.fb.facebook_clone.exceptions;

public class PostException extends Exception{
    public PostException(String message) {
        super("PostException: %s".formatted(message));
    }
}