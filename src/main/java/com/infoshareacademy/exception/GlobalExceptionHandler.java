package com.infoshareacademy.exception;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String methodArgumentNotValidException(Exception ex) {
        log.error("An unexpected error has happened", ex);
        return "An internal error has happened, please report the incident";
    }

    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public String notFoundExceptionMethod(NotFoundException ex){
        log.error("The object you were looking for are not found!", ex);
        return "Sorry, can't find what you were looking for!";
    }

    @ResponseStatus(NO_CONTENT)
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionMethod(NullPointerException ex){
        log.error("There is a null", ex);
        return "Nope, there is nothing...";
    }

    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public String usernameNotFoundExceptionMethod(UsernameNotFoundException ex){
        log.error("Can't find this username", ex);
        return "Can't find this username";
    }



}
