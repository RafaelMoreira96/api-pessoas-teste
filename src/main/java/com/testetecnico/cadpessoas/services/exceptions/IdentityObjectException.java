package com.testetecnico.cadpessoas.services.exceptions;

public class IdentityObjectException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IdentityObjectException(String message, Throwable cause) {
		super(message, cause);
	}

    public IdentityObjectException(String message) {
		super(message);
	}
}
