package com.openwebinars.rest.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ProductoNoContentException extends RuntimeException {

    private static final long serialVersionUID = 533132321432321L;

    public ProductoNoContentException(Long id) {
        super("No existe el contenido con ID: " + id);
    }

}