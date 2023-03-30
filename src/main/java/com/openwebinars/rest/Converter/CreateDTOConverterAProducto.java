package com.openwebinars.rest.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.openwebinars.rest.Model.Producto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateDTOConverterAProducto {
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CreateDTOConverterAProducto.class);

    public Producto convertToProducto(CreateProductoDTO productoDTO) {
        logger.info("Dentro de convert productodto: " + productoDTO.toString());
        return modelMapper.map(productoDTO, Producto.class);
    }

}
