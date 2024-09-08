package com.danielpm1982.springboot3_profile_manager.exception;

public class PersonSaveOrUpdateByIdFailedException extends RuntimeException{
    private String message;

    public PersonSaveOrUpdateByIdFailedException() {}

    public PersonSaveOrUpdateByIdFailedException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
