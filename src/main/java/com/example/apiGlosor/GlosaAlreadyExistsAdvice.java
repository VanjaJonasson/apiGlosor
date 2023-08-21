package com.example.apiGlosor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlosaAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(GlosaAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String glosaAlreadyExistsHandler(GlosaAlreadyExistsException ex){
        return ex.getMessage();
    }
}
