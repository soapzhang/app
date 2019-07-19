package com.soap.app.Exception;

public class PlatformUncheckException extends RuntimeException {

    private static final long serialVersionUID = -4193482020615580512L;

    /**
     *
     */
    public PlatformUncheckException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public PlatformUncheckException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public PlatformUncheckException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public PlatformUncheckException(Throwable cause) {
        super(cause);
    }
}
