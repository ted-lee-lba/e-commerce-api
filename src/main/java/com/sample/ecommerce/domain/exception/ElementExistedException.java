package com.sample.ecommerce.domain.exception;

public class ElementExistedException extends Exception {
    public ElementExistedException() {
    }

    public ElementExistedException(String message) {
        super(message);
    }
}
