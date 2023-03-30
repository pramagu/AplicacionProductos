package com.openwebinars.rest.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openwebinars.rest.Service.ServicioProductos;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PedidoController {

	@Autowired
	private final ServicioProductos servicio;

	@GetMapping("/pedido")
	public ResponseEntity<?> obtenerTodosPedidos(@PageableDefault(size = 10, page = 0) Pageable pageable,
			HttpServletRequest request) {

		return servicio.obtenerPedidos(pageable, request);

	}

}
