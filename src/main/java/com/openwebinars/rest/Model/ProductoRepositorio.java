package com.openwebinars.rest.Model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "productosHal", collectionResourceRel = "productos")
public interface ProductoRepositorio extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {

    Page<Producto> findByNombreContainsIgnoreCase(String txt, Pageable pageable);

    @RestResource(path = "productoComienzaPor", rel = "produtoComienzaPor")
    Page<Producto> findByNombreStartsWith(@Param("nombre") String nombre, Pageable pageable);

}
