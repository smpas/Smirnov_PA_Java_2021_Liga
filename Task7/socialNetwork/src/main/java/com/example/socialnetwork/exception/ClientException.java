package com.example.socialnetwork.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientException extends RuntimeException {
    private String exceptionCause;

    public ClientException(String exceptionCause) {
        super(String.format("Incorrect request: %s", exceptionCause));
        this.exceptionCause = exceptionCause;
    }
}
