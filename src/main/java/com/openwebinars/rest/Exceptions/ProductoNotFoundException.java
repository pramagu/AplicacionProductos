package com.openwebinars.rest.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 433132321432321L;

    public ProductoNotFoundException(Long id) {
        super("No se puede encontrar el producto con el ID: " + id);
    }

    public ProductoNotFoundException() {
        super("No existe ningún Producto");
    }
}