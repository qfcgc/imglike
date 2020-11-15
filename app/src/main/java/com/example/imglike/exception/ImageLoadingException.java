package com.example.imglike.exception;

public class ImageLoadingException extends RuntimeException {
    public ImageLoadingException() {
    }

    public ImageLoadingException(String message) {
        super(message);
    }

    public ImageLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageLoadingException(Throwable cause) {
        super(cause);
    }
}
