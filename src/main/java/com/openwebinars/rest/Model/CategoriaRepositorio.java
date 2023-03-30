package com.openwebinars.rest.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "categorias", collectionResourceRel = "categorias")
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

}
