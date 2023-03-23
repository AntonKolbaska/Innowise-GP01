package com.gproject.exception;

public class NonExistentEntityException extends Throwable {
    public NonExistentEntityException(String message) {
        super(message);
    }
}
