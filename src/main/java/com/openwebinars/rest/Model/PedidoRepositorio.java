package com.openwebinars.rest.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pedidosHal", collectionResourceRel = "pedidos")
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

}
