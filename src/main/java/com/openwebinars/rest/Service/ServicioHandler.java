package com.openwebinars.rest.Service;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.openwebinars.rest.Exceptions.ApiError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioHandler {

    public ResponseEntity<ApiError> constructorHandlerNotFound(Exception excepcion, HttpStatus httpStatus) {
        ApiError apiError = new ApiError(httpStatus, excepcion.getMessage());
        apiError.setFecha(LocalDateTime.now());
        return ResponseEntity.status(httpStatus).body(apiError);
    }

    public ResponseEntity<Object> constructorHandlerPeticionGeneral(Exception excepcion, Object body,
            HttpHeaders cabeceras,
            HttpStatus estado, WebRequest peticion) {
        ApiError apiError = new ApiError(estado, excepcion.getMessage());
        apiError.setFecha(LocalDateTime.now());
        return ResponseEntity.status(estado).headers(cabeceras).body(apiError);
    }

}
