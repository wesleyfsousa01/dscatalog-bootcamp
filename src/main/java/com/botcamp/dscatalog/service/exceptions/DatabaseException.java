package com.botcamp.dscatalog.service.exceptions;

public class DatabaseException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    public DatabaseException(String msg){
        super(msg);
    }
}
