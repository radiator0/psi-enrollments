package com.psi.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UnauthorizedException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException() {
        super(ErrorConstants.INVALID_PASSWORD_TYPE, "Unauthorized", Status.UNAUTHORIZED);
    }
}
