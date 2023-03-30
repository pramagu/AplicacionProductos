package com.openwebinars.rest.Exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.openwebinars.rest.Service.ServicioHandler;

@RestControllerAdvice
public class ControladorGlobalErrores extends ResponseEntityExceptionHandler {

	@Autowired
	ServicioHandler servicio;

	@ExceptionHandler(ProductoNotFoundException.class)
	public ResponseEntity<ApiError> handleProductoNoEncontrado(ProductoNotFoundException excepcionProducto) {
		return servicio.constructorHandlerNotFound(excepcionProducto, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception excepcion, @Nullable Object body,
			HttpHeaders cabeceras,
			HttpStatus estado, WebRequest peticion) {
		return servicio.constructorHandlerPeticionGeneral(excepcion, body, cabeceras, estado, peticion);
	}

}