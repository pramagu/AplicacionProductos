package com.openwebinars.rest.Converter;

import com.fasterxml.jackson.annotation.JsonView;
import com.openwebinars.rest.views.ProductoViews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    @JsonView(ProductoViews.Dto.class)
    private String nombre;
    @JsonView(ProductoViews.DtoConPrecio.class)
    private float precio;
    @JsonView(ProductoViews.Dto.class)
    private String categoriaNombre;
}
