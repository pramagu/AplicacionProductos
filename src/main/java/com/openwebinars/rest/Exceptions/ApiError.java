package com.openwebinars.rest.Exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiError {

    private HttpStatus estado;
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime fecha;
    private String mensaje;

    public ApiError(HttpStatus estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

}