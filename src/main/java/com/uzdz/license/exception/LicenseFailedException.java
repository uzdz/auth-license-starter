package com.uzdz.license.exception;


public class LicenseFailedException extends RuntimeException {
    private static final long serialVersionUID = 5009965954277376931L;

    public LicenseFailedException(String message) {
        super(message);
    }

    public LicenseFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}