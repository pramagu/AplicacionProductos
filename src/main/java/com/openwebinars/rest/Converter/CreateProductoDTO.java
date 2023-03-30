package com.openwebinars.rest.Converter;

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
public class CreateProductoDTO {
    private String nombre;
    private float precio;
    private String imagen;
    private Long categoriaId;
}
