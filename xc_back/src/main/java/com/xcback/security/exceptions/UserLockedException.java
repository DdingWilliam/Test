package com.xcback.security.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UserLockedException extends AuthenticationException {

    public UserLockedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserLockedException(String explanation) {
        super(explanation);
    }
}
