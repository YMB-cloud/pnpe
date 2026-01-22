package com.app.auth;


import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class GlobalExceptionHandlerFactory extends ExceptionHandlerFactory {

    private final ExceptionHandlerFactory parent;

    public GlobalExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new GlobalExceptionHandler(parent.getExceptionHandler());
    }
}

