package com.psi.service;

public class InsufficientUserPermissionsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InsufficientUserPermissionsException() {
        super("User don't have rights to cast to this object");
    }

    public InsufficientUserPermissionsException(String append){
        super(String.format("User don't have rights to cast to this object. (%s)", append));
    }

}
