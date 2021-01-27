package com.psi.service;

public class RequestAlreadyExaminedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RequestAlreadyExaminedException() {
        super("Request is already examined!");
    }

}
