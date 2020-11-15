package com.example.imglike;

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

    public ImageLoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
