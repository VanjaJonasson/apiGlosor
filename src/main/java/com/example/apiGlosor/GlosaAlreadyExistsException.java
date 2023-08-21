package com.example.apiGlosor;

public class GlosaAlreadyExistsException extends RuntimeException{
    public GlosaAlreadyExistsException(int id) {
        super("Glosa with id " + id + " already exists");
    }
}
