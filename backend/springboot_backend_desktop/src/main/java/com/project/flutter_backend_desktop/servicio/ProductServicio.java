package com.project.flutter_backend_desktop.servicio;

import Excepcion.RecursoNoEncontradoException;
import com.project.flutter_backend_desktop.modelo.*;
import com.project.flutter_backend_desktop.modelo.Dto.ProductDTO;
import com.project.flutter_backend_desktop.modelo.Enum.EstadoProducto;
import com.project.flutter_backend_desktop.modelo.Enum.TipoPromocion;
import com.project.flutter_backend_desktop.modelo.Record.ProductoAutoDTO;
import com.project.flutter_backend_desktop.modelo.Request.PromoBonoRequestDTO;
import com.project.flutter_backend_desktop.modelo.Response.PromoBonoResponseDTO;
import com.project.flutter_backend_desktop.repositorio.CategoriaRepositorio;
import com.project.flutter_backend_desktop.repositorio.ClasificacionRepositorio;
import com.project.flutter_backend_desktop.repositorio.InventarioRepositorio;
import com.project.flutter_backend_desktop.repositorio.ProductRepositorio;
import com.project.flutter_backend_desktop.servicio.interfaz.IProductServicio;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.project.flutter_backend_desktop.Constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class ProductServicio implements IProductServicio {

    @Autowired
    private ProductRepositorio productRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ClasificacionRepositorio clasificacionRepositorio;

    @Autowired
    private InventarioRepositorio inventarioRepositorio;

    private static final Logger log = LoggerFactory.getLogger(ProductServicio.class);

    public Product getProduct(String id) {
        return productRepositorio.findById(Integer.valueOf(id)).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public List<Product> listarProducts() {
        return productRepositorio.findAllOrderByEstadoPrioridad();
    }

    @Override
    public Product buscarProductPorId(Integer idProduct) {
        Product product = productRepositorio.findById(idProduct).orElse(null);
        return product;
    }

    @Transactional
    @Override
    public Product guardarProduct(ProductDTO dto) {
        Categoria categoria = categoriaRepositorio.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Clasificacion clasificacion = clasificacionRepositorio.findById(dto.getClasificacionId())
                .orElseThrow(() -> new RuntimeException("Clasificación no encontrada"));

        Product product = new Product();

        product.setNombre(dto.getNombre());
        product.setContenido(dto.getContenido());
        product.setPrecio(dto.getPrecio());
        product.setMarca(dto.getMarca());
        product.setFotoUrl(dto.getFotoUrl());
        product.setEstado(EstadoProducto.ACTIVO);
        product.setFrecuente(
                dto.getFrecuente() != null ? dto.getFrecuente() : false);
        product.setCategoria(categoria);
        product.setClasificacion(clasificacion);

        Product guardado = productRepositorio.save(product);

        // Generar código
        guardado.setCodigo(String.format("PROD-%06d", guardado.getIdProduct()));

        Inventario inventario = new Inventario();

        inventario.setProducto(guardado);
        inventario.setStockActual(0);
        inventario.setFechaActualizacion(LocalDateTime.now());

        inventarioRepositorio.save(inventario);

        return guardado;
    }

    @Transactional
    @Override
    public Product actualizarProduct(ProductDTO dto, Integer idProduct) {
        var productoActual = productRepositorio.findById(idProduct).orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        Categoria categoria = categoriaRepositorio.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Clasificacion clasificacion = clasificacionRepositorio.findById(dto.getClasificacionId())
                .orElseThrow(() -> new RuntimeException("Clasificacion no encontrada"));

        productoActual.setNombre(dto.getNombre());
        productoActual.setMarca(dto.getMarca());
        productoActual.setContenido(dto.getContenido());
        productoActual.setPrecio(dto.getPrecio());
        productoActual.setFotoUrl(dto.getFotoUrl());
        productoActual.setEstado(dto.getEstado());
        productoActual.setFrecuente(
                dto.getFrecuente() != null ? dto.getFrecuente() : false);
        productoActual.setCategoria(categoria);
        productoActual.setClasificacion(clasificacion);

        return productRepositorio.save(productoActual);
    }

    @Override
    public void eliminarProduct(Product product) {
        productRepositorio.delete(product);
    }

    @Override
    public void deleteProduct(Product product) {
        if (product.getEstado() != EstadoProducto.DESCONTINUADO) {
            throw new RuntimeException("Debe descontinuar el producto antes de eliminarlo");
        }

        productRepositorio.delete(product);
    }

//    @Override
//    public String buscarCodigo() {
//        String searchCodigo = productRepositorio.buscarCodigo();
//        int nuevoCodigo = Integer.parseInt(searchCodigo);
//        return String.valueOf(nuevoCodigo);
//    }

    @Override
    public String buscarCodigo() {
        String ultimo = productRepositorio.buscarCodigo();

        if (ultimo == null) {
            return "PROD-000001";
        }

        int numero = Integer.parseInt(ultimo.replace("PROD-", ""));
        return String.format("PROD-%06d", numero + 1);
    }

    // Funcion de Servicio de foto I
    public String uploadFoto(String id, MultipartFile file) {
        log.info("Mensaje de foto para el usuario por ID: {}", id);
        Product product = getProduct(id);
        String fotoUrl = fotoFuncional.apply(id, file);
        product.setFotoUrl(fotoUrl);
        productRepositorio.save(product);
        return fotoUrl;
    }


    // Funcion de Servicio de foto III
    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name ->name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") +1)).orElse(".png");

    // Funcion de Servicio de foto II
    private final BiFunction<String, MultipartFile, String> fotoFuncional = (id, image) -> {
        String filename = id + fileExtension.apply(image.getOriginalFilename());

        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)) { Files.createDirectories(fileStorageLocation); }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
//            return ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/flutterDesktop/productos/image/" + filename)
//                    .toUriString();
            return filename;
        } catch (Exception exception) {
            log.error("Error al guardar la imagen: ", exception);
            throw new RuntimeException("No se puede guardar la imagen" + exception.getMessage(), exception);
        }
    };

    @Override
    public List<Product> listarFrecuentes() {
        return productRepositorio.findByFrecuenteTrue();
    }

    @Transactional
    @Override
    public Product cambiarEstadoProducto(Integer idProduct, EstadoProducto estado) {

        Product producto = productRepositorio.findById(idProduct)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getEstado() == EstadoProducto.DESCONTINUADO) {
            throw new RuntimeException("El producto ya está descontinuado");
        }

        producto.setEstado(estado);

        return productRepositorio.save(producto);
    }

    @Override
    public List<Product> filtrarProductos(
            String codigo,
            String nombre,
            String marca,
            Integer idCategoria,
            Integer idClasificacion,
            EstadoProducto estado,
            Double precioMin,
            Double precioMax,
            String sortBy,
            String direction) {
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if (codigo != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("codigo"), "%" + codigo + "%"));
        }

        if (nombre != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("nombre"), "%" + nombre + "%"));
        }

        if (marca != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("marca"), "%" + marca + "%"));
        }

        if (idCategoria != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("categoria").get("idCategoria"), idCategoria));
        }

        if (idClasificacion != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("clasificacion").get("idClasificacion"), idClasificacion));
        }

        if (estado != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("estado").get("estado"), estado));
        }

        // rango de precios
        if (precioMin != null && precioMax != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("precio"), precioMin, precioMax));
        } else if (precioMin != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("precio"), precioMin));
        } else if (precioMax != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("precio"), precioMax));
        }

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        return productRepositorio.findAll(spec, sort);
    }

    public List<ProductoAutoDTO> autocompleteProductos(String nombre) {
        return productRepositorio
                .findTop10ByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(p -> new ProductoAutoDTO(
                        p.getIdProduct(),
                        p.getNombre(),
                        p.getMarca(),
                        p.getCodigo(),
                        p.getPrecio()
                ))
                .toList();
    }

    @Override
    public Page<Product> filtrarProductosPaginado(
            String codigo,
            String nombre,
            String marca,
            Integer idCategoria,
            Integer idClasificacion,
            EstadoProducto estado,
            Double precioMin,
            Double precioMax,
            Integer page,
            Integer size,
            String sortBy,
            String direction
    ) {
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if (codigo != null && !codigo.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("codigo"), "%" + codigo + "%"));
        }

        if (nombre != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("nombre"), "%" + nombre + "%"));
        }

        if (marca != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("marca"), "%" + marca + "%"));
        }

        if (idCategoria != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("categoria").get("idCategoria"), idCategoria));
        }

        if (idClasificacion != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("clasificacion").get("idClasificacion"), idClasificacion));
        }

        if (estado != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("estado").get("estado"), estado));
        }

        // rango de precios
        if (precioMin != null && precioMax != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("precio"), precioMin, precioMax));
        } else if (precioMin != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("precio"), precioMin));
        } else if (precioMax != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("precio"), precioMax));
        }

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepositorio.findAll(spec, pageable);
    }

    @Override
    public List<Product> topVendidosMes(int mes, int anio, int limit) {
        return productRepositorio.topProductosMasVendidos(
                mes,
                anio,
                PageRequest.of(0, limit)
        );
    }

    @Override
    public List<Product> menosVendidosMes(int mes, int anio, int limit) {
        return productRepositorio.topProductosMenosVendidos(
                mes,
                anio,
                PageRequest.of(0, limit)
        );
    }

    @Override
    public List<PromoBonoResponseDTO> listarBonificacionesProducto(Integer idProduct) {
        Product producto = buscarProductPorId(idProduct);

        return producto.getBonificaciones()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PromoBonoResponseDTO agregarBonificacionProducto(Integer idProduct, PromoBonoRequestDTO promoBonoDto) {
        Product producto = buscarProductPorId(idProduct);

        Product productoBono =
                buscarProductPorId(
                        promoBonoDto.getProductoBonoId()
                );

        if (productoBono.getEstado() != EstadoProducto.ACTIVO) {
            throw new RuntimeException(
                    "El producto bono no está activo"
            );
        }

        validarPromocion(promoBonoDto);

        boolean existe = producto.getBonificaciones()
                .stream()
                .anyMatch(b ->

                        b.getProductoBonificacion()
                                .getIdProduct()
                                .equals(promoBonoDto.getProductoBonoId())

                                &&

                                b.getTipoPromocion()
                                        .equals(promoBonoDto.getTipoPromocion())

                                &&

                                (
                                        b.getCantidadObjetivo() != null
                                                &&
                                                b.getCantidadObjetivo()
                                                        .equals(
                                                                promoBonoDto.getCantidadObjetivo()
                                                        )
                                                ||

                                                b.getMontoObjetivo() != null
                                                        &&
                                                        b.getMontoObjetivo()
                                                                .equals(
                                                                        promoBonoDto.getMontoObjetivo()
                                                                )
                                )
                );

        if (existe) {
            throw new RuntimeException(
                    "Ya existe una promoción igual"
            );
        }

        PromoBonoProduct bonificacion =
                new PromoBonoProduct();

        bonificacion.setProductoOrigen(producto);

        bonificacion.setProductoBonificacion(productoBono);

        bonificacion.setNombre(
                promoBonoDto.getNombre()
        );

        bonificacion.setTipoPromocion(
                promoBonoDto.getTipoPromocion()
        );

        bonificacion.setMontoObjetivo(
                promoBonoDto.getMontoObjetivo()
        );

        bonificacion.setCantidadObjetivo(
                promoBonoDto.getCantidadObjetivo()
        );

        bonificacion.setCantidadBonificacion(
                promoBonoDto.getCantidadBonificacion()
        );

        bonificacion.setFechaInicio(
                promoBonoDto.getFechaInicio()
        );

        bonificacion.setFechaFin(
                promoBonoDto.getFechaFin()
        );

        bonificacion.setActivo(
                promoBonoDto.getActivo() != null
                        ? promoBonoDto.getActivo()
                        : true
        );

        producto.getBonificaciones()
                .add(bonificacion);

        productRepositorio.save(producto);

        return mapToResponse(bonificacion);
    }

    @Override
    public PromoBonoResponseDTO actualizarBonificacionProducto(Integer idProduct, Integer idBonificacion, PromoBonoRequestDTO promoBonoDto) {
        Product producto = buscarProductPorId(idProduct);

        validarPromocion(promoBonoDto);

        boolean existe = producto.getBonificaciones()
                .stream()
                .anyMatch(b ->

                        b.getProductoBonificacion()
                                .getIdProduct()
                                .equals(promoBonoDto.getProductoBonoId())

                                &&

                                b.getTipoPromocion()
                                        .equals(promoBonoDto.getTipoPromocion())

                                &&

                                (
                                        b.getCantidadObjetivo() != null
                                                &&
                                                b.getCantidadObjetivo()
                                                        .equals(
                                                                promoBonoDto.getCantidadObjetivo()
                                                        )
                                                ||

                                                b.getMontoObjetivo() != null
                                                        &&
                                                        b.getMontoObjetivo()
                                                                .equals(
                                                                        promoBonoDto.getMontoObjetivo()
                                                                )
                                )
                );

        if (existe) {
            throw new RuntimeException(
                    "Ya existe una promoción igual"
            );
        }

        Product productoBono =
                buscarProductPorId(
                        promoBonoDto.getProductoBonoId()
                );

        if (productoBono.getEstado() != EstadoProducto.ACTIVO) {
            throw new RuntimeException(
                    "El producto bono no está activo"
            );
        }

        PromoBonoProduct bonificacion =
                producto.getBonificaciones()
                        .stream()
                        .filter(b ->
                                b.getIdPromoBonoProduct()
                                        .equals(idBonificacion)
                        )
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Bonificación no encontrada"
                                )
                        );

        bonificacion.setProductoBonificacion(productoBono);

        bonificacion.setNombre(
                promoBonoDto.getNombre()
        );

        bonificacion.setTipoPromocion(
                promoBonoDto.getTipoPromocion()
        );

        bonificacion.setMontoObjetivo(
                promoBonoDto.getMontoObjetivo()
        );

        bonificacion.setCantidadObjetivo(
                promoBonoDto.getCantidadObjetivo()
        );

        bonificacion.setCantidadBonificacion(
                promoBonoDto.getCantidadBonificacion()
        );

        bonificacion.setFechaInicio(
                promoBonoDto.getFechaInicio()
        );

        bonificacion.setFechaFin(
                promoBonoDto.getFechaFin()
        );

        bonificacion.setActivo(
                promoBonoDto.getActivo()
        );

        productRepositorio.save(producto);

        return mapToResponse(bonificacion);
    }

    @Override
    public void eliminarBonificacionProducto(Integer idProduct, Integer idBonificacion) {
        Product producto = buscarProductPorId(idProduct);
        producto.getBonificaciones().removeIf(b -> b.getIdPromoBonoProduct().equals(idBonificacion));
        productRepositorio.save(producto);
    }

    private void validarPromocion(PromoBonoRequestDTO dto) {

        if (dto.getTipoPromocion() == TipoPromocion.POR_MONTO) {

            if (dto.getMontoObjetivo() == null
                    || dto.getMontoObjetivo() <= 0) {

                throw new RuntimeException(
                        "Monto objetivo inválido"
                );
            }

            dto.setCantidadObjetivo(null);
        }

        if (dto.getTipoPromocion() == TipoPromocion.POR_CANTIDAD) {

            if (dto.getCantidadObjetivo() == null
                    || dto.getCantidadObjetivo() <= 0) {

                throw new RuntimeException(
                        "Cantidad objetivo inválida"
                );
            }

            dto.setMontoObjetivo(null);
        }

        if (dto.getCantidadBonificacion() == null
                || dto.getCantidadBonificacion() <= 0) {

            throw new RuntimeException(
                    "Cantidad bonificación inválida"
            );
        }

        if (dto.getFechaInicio() != null
                && dto.getFechaFin() != null
                        && dto.getFechaFin().isBefore(
                                dto.getFechaInicio()
                        )
        ) {

            throw new RuntimeException(
                    "La fecha fin no puede ser menor a la fecha inicio"
            );
        }
    }

    private PromoBonoResponseDTO mapToResponse(PromoBonoProduct promo) {
        return new PromoBonoResponseDTO(
                promo.getIdPromoBonoProduct(),
                promo.getNombre(),
                promo.getProductoOrigen().getNombre(),
                promo.getProductoBonificacion().getNombre(),
                promo.getTipoPromocion(),
                promo.getMontoObjetivo(),
                promo.getCantidadObjetivo(),
                promo.getCantidadBonificacion(),
                promo.getFechaInicio(),
                promo.getFechaFin(),
                promo.getActivo()
        );
    }
}