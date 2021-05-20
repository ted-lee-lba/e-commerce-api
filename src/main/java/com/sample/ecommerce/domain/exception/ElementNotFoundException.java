package com.sample.ecommerce.domain.exception;

public class ElementNotFoundException extends Exception {
    public ElementNotFoundException() {
    }

    public ElementNotFoundException(String message) {
        super(message);
    }
}
