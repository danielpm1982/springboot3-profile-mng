package com.danielpm1982.springboot3_profile_manager.exception;

public class PersonDeleteByIdFailedException extends RuntimeException{
    private String message;

    public PersonDeleteByIdFailedException() {}

    public PersonDeleteByIdFailedException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
