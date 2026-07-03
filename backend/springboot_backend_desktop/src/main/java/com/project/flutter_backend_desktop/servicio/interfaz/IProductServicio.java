package com.project.flutter_backend_desktop.servicio.interfaz;

import com.project.flutter_backend_desktop.modelo.Dto.ProductDTO;
import com.project.flutter_backend_desktop.modelo.Enum.EstadoProducto;
import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.modelo.PromoBonoProduct;
import com.project.flutter_backend_desktop.modelo.Record.ProductoAutoDTO;
import com.project.flutter_backend_desktop.modelo.Request.PromoBonoRequestDTO;
import com.project.flutter_backend_desktop.modelo.Response.PromoBonoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductServicio {

    List<Product> listarProducts();
    Product guardarProduct(ProductDTO productDTO);
    Product actualizarProduct(ProductDTO productDTO, Integer idProduct);
    void eliminarProduct(Product product);
    void deleteProduct(Product product);
    Product buscarProductPorId(Integer idProduct);

    String buscarCodigo();

    String uploadFoto(String idProduct, MultipartFile file);

    List<Product> listarFrecuentes();

    Product cambiarEstadoProducto(Integer idProduct, EstadoProducto estado);

    List<Product> filtrarProductos(
            String codigo,
            String nombre,
            String marca,
            Integer idCategoria,
            Integer idClasificacion,
            EstadoProducto estado,
            Double precioMin,
            Double precioMax,
            String sortBy,
            String direction
    );

    List<ProductoAutoDTO> autocompleteProductos (String nombre);

    Page<Product> filtrarProductosPaginado(
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
    );

    List<Product> topVendidosMes(int mes, int anio, int limit);

    List<Product> menosVendidosMes(int mes, int anio, int limit);

    // Bonificaciones
    List<PromoBonoResponseDTO> listarBonificacionesProducto(Integer idProduct);
    PromoBonoResponseDTO agregarBonificacionProducto(Integer idProduct, PromoBonoRequestDTO promoBonoDto);
    PromoBonoResponseDTO actualizarBonificacionProducto(Integer idProduct, Integer idBonificacion, PromoBonoRequestDTO promoBonoDto);
    void eliminarBonificacionProducto(Integer idProduct, Integer idBonificacion);
}