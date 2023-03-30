package com.openwebinars.rest.Converter;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.openwebinars.rest.Exceptions.ProductoNotFoundException;
import com.openwebinars.rest.Model.Producto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoDTOConverter {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<Producto, ProductoDTO>() {

            @Override
            protected void configure() {
                map().setCategoriaNombre(source.getCategoria().getNombre());
            }

        });
    }

    public ProductoDTO convertToDto(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }

    public Object convertToDto(Optional<Producto> findById, Long id) {
        Producto result = findById.orElseThrow(() -> new ProductoNotFoundException(id));
        return modelMapper.map(result, ProductoDTO.class);
    }

    public ProductoDTO convertToDtoLombok(Producto producto) {

        return ProductoDTO.builder()
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .categoriaNombre(producto.getNombre())
                .build();
    }

}
