package com.example.apiGlosor;

public class GlosaNotFoundException extends RuntimeException{
    public GlosaNotFoundException(int id) {
        super("Could not find glosa or category with id " + id);
    }
}
