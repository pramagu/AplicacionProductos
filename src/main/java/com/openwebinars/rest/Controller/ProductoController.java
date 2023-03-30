package com.openwebinars.rest.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.openwebinars.rest.Converter.CreateProductoDTO;
import com.openwebinars.rest.Service.ServicioProductos;
import com.openwebinars.rest.views.ProductoViews;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ProductoController {

	@Autowired
	private final ServicioProductos servicio;

	@GetMapping("/producto/todo")
	public ResponseEntity<?> obtenerTodos(@PageableDefault(size = 10, page = 0) Pageable pageable,
			HttpServletRequest request) {

		return servicio.obtenerProductos(pageable, request);

	}

	@GetMapping(value = "/producto/todo", params = "nombre")
	public ResponseEntity<?> buscarProductosPorNombre(@RequestParam("nombre") String txt,
			Pageable pageable, HttpServletRequest request) {
		return servicio.obtenerProductosPorNombre(pageable, request, txt);
	}

	@GetMapping("/producto")
	public ResponseEntity<?> buscarProductosPorVarios(
			@RequestParam("nombre") Optional<String> nombre,
			@RequestParam("precio") Optional<Float> precio,
			@PageableDefault(size = 10, page = 0) Pageable pageable, HttpServletRequest request) {

		return servicio.obtenerProductosCriterion(nombre, precio, pageable, request);

	}

	@JsonView(ProductoViews.DtoConPrecio.class)
	@GetMapping("/producto/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable Long id) {

		return servicio.obtenerUnProducto(id);

	}

	@PostMapping("/producto")
	public ResponseEntity<?> nuevoProducto(@RequestBody CreateProductoDTO producto) {

		return servicio.insertarProducto(producto);

	}

	@PostMapping(value = "/producto/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> nuevoProductoImagen(@RequestPart("nuevo") CreateProductoDTO nuevoProductoDTO,
			@RequestPart("file") MultipartFile file) {

		return servicio.insertarProductoConFichero(nuevoProductoDTO, file);

	}

	@PutMapping("/producto/{id}")
	public ResponseEntity<?> editarProducto(@RequestBody CreateProductoDTO editar, @PathVariable Long id) {

		return servicio.actualizarUnProducto(editar, id);

	}

	@DeleteMapping("/producto/{id}")
	public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
		return servicio.borrarUnProducto(id);
	}

}
