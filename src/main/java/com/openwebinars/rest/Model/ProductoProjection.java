package com.openwebinars.rest.Model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "productoSinCategoriaNiImagen", types = { Producto.class })
public interface ProductoProjection {

	String getNombre();

	Float getPrecio();

}
