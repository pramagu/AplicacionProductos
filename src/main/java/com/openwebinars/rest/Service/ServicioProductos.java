package com.openwebinars.rest.Service;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.openwebinars.rest.Controller.FicherosController;
import com.openwebinars.rest.Converter.CreateDTOConverterAProducto;
import com.openwebinars.rest.Converter.CreateProductoDTO;
import com.openwebinars.rest.Converter.ProductoDTO;
import com.openwebinars.rest.Converter.ProductoDTOConverter;
import com.openwebinars.rest.Exceptions.ProductoNotFoundException;
import com.openwebinars.rest.Model.Categoria;
import com.openwebinars.rest.Model.CategoriaRepositorio;
import com.openwebinars.rest.Model.Pedido;
import com.openwebinars.rest.Model.PedidoRepositorio;
import com.openwebinars.rest.Model.Producto;
import com.openwebinars.rest.Model.ProductoRepositorio;
import com.openwebinars.rest.Utils.PaginationLinksUtils;
import com.openwebinars.rest.upload.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioProductos extends ServicioBase<Producto, Long, ProductoRepositorio> {

    @Autowired
    private final ProductoDTOConverter productoDTOConverter;
    private final CreateDTOConverterAProducto conversorProducto;
    private final StorageService servicioStorage;
    private final CategoriaRepositorio categoriaRepositorio;
    private final PaginationLinksUtils paginationLinksUtils;
    private final PedidoRepositorio pedidoRepositorio;

    public ResponseEntity<?> obtenerProductos(Pageable pageable, HttpServletRequest request) {

        Boolean checkDeSiEstaVacio = this.findAll(pageable).isEmpty();
        Page<ProductoDTO> dtoList = null;
        UriComponentsBuilder uriBuilder = null;

        if (!checkDeSiEstaVacio) {
            dtoList = this.findAll(pageable).map(productoDTOConverter::convertToDto);

            uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

        } else
            throw new ProductoNotFoundException();

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
                .body(dtoList);

    }

    public ResponseEntity<?> obtenerPedidos(Pageable pageable, HttpServletRequest request) {

        Boolean checkDeSiEstaVacio = pedidoRepositorio.findAll(pageable).isEmpty();
        Page<Pedido> dtoList = null;
        UriComponentsBuilder uriBuilder = null;

        if (!checkDeSiEstaVacio) {
            dtoList = pedidoRepositorio.findAll(pageable);

            uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

        } else
            throw new ProductoNotFoundException();

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
                .body(dtoList);

    }

    public ResponseEntity<?> obtenerProductosPorNombre(Pageable pageable, HttpServletRequest request, String txt) {

        Boolean checkDeSiEstaVacio = this.findByNombre(txt, pageable).isEmpty();
        Page<ProductoDTO> dtoList = null;
        UriComponentsBuilder uriBuilder = null;

        if (!checkDeSiEstaVacio) {
            dtoList = this.findByNombre(txt, pageable).map(productoDTOConverter::convertToDto);
            uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

        } else
            throw new ProductoNotFoundException();

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
                .body(dtoList);

    }

    public ResponseEntity<?> obtenerProductosCriterion(Optional<String> nombre, Optional<Float> precio,
            Pageable pageable, HttpServletRequest request) {

        Boolean checkDeSiEstaVacio = this.findByArgs(nombre, precio, pageable).isEmpty();
        Page<ProductoDTO> dtoList = null;
        UriComponentsBuilder uriBuilder = null;

        if (!checkDeSiEstaVacio) {
            dtoList = this.findByArgs(nombre, precio, pageable).map(productoDTOConverter::convertToDto);
            uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

        } else
            throw new ProductoNotFoundException();

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
                .body(dtoList);

    }

    public ResponseEntity<?> obtenerUnProducto(Long id) {

        return ResponseEntity.ok(productoDTOConverter.convertToDto(this.findById(id), id));

    }

    public ResponseEntity<?> insertarProducto(CreateProductoDTO nuevo) {

        Producto nuevoProducto = new Producto();
        Categoria categoria = categoriaRepositorio.findById(nuevo.getCategoriaId()).orElse(null);
        nuevoProducto = conversorProducto.convertToProducto(nuevo);
        nuevoProducto.setId(null);
        nuevoProducto.setCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.save(nuevoProducto));
    }

    public ResponseEntity<?> insertarProductoConFichero(CreateProductoDTO nuevo,
            MultipartFile file) {

        String urlImagen = null;

        if (!file.isEmpty()) {
            String imagen = servicioStorage.store(file);
            urlImagen = MvcUriComponentsBuilder.fromMethodName(FicherosController.class, "serveFile", imagen, null)
                    .build().toUriString();
        }

        Producto nuevoProducto = Producto.builder()
                .nombre(nuevo.getNombre())
                .precio(nuevo.getPrecio())
                .imagen(urlImagen)
                .categoria(categoriaRepositorio.findById(nuevo.getCategoriaId()).orElse(null))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(this.save(nuevoProducto));
    }

    public ResponseEntity<?> actualizarUnProducto(CreateProductoDTO productoBody, Long id) {

        return this.findById(id).map(nuevoProducto -> {
            (nuevoProducto = conversorProducto.convertToProducto(productoBody)).setId(id);
            return ResponseEntity.ok(this.save(nuevoProducto));
        }).orElseThrow(() -> new ProductoNotFoundException(id));

    }

    public ResponseEntity<?> borrarUnProducto(Long id) {
        this.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));
        this.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public Page<Producto> findByNombre(String txt, Pageable pageable) {
        return this.repositorio.findByNombreContainsIgnoreCase(txt, pageable);
    }

    public Page<Producto> findByArgs(final Optional<String> nombre, final Optional<Float> precio, Pageable pageable) {
        Specification<Producto> specNombreProducto = new Specification<Producto>() {

            @Override
            @Nullable
            public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query,
                    CriteriaBuilder criteriaBuilder) {
                if (nombre.isPresent()) {
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.get() + "%");
                } else {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }

        };

        Specification<Producto> precioMenorQue = new Specification<Producto>() {

            @Override
            @Nullable
            public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query,
                    CriteriaBuilder criteriaBuilder) {
                if (precio.isPresent()) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precio.get());
                } else {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }

        };

        Specification<Producto> ambas = specNombreProducto.and(precioMenorQue);

        return this.repositorio.findAll(ambas, pageable);
    }
}
