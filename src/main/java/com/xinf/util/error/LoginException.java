package com.xinf.util.error;

/**
 * @author xinf
 * @since 2021/9/1 17:46
 */
public class LoginException extends RuntimeException {

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
