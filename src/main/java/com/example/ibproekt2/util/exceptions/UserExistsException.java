package com.example.ibproekt2.util.exceptions;

public class UserExistsException extends Exception{
    @Override
    public String getMessage() {
        return "UserExistsException: " + super.getMessage();
    }
}
