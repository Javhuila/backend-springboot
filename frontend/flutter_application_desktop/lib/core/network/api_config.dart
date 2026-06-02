import 'dart:io' show Platform;
import 'package:flutter/foundation.dart';

class ApiConfig {
  static String get baseUrl {
    if (kIsWeb) {
      return 'http://localhost:8080';
    }

    if (Platform.isAndroid) {
      return 'http://192.168.1.104:8080';
    }

    if (Platform.isWindows || Platform.isMacOS || Platform.isLinux) {
      return 'http://localhost:8080';
    }

    // iOS o teléfono físico
    return 'http://192.168.1.104:8080'; // TU IP LOCAL
    // return 'http://10.0.2.2:8080'; TU IP LOCAL
  }

  static const String productEndpoint = '/api/product/productos';
  static const String productEditEndpoint = '/api/product/editProductos';

  static const String topVendidos = '/api/product/productos/top-vendidos';
  static const String menosVendidos = '/api/product/productos/menos-vendidos';
  static const String topVendidosMesActual =
      '/api/product/productos/top-vendidos/mes-actual';

  static const String categoryEndpoint = '/api/category/categorias';
  static const String categoryEditEndpoint = '/api/category/editCategorias';

  static const String clasificationEndpoint =
      '/api/clasification/clasificaciones';
  static const String clasificationEditEndpoint =
      '/api/clasification/editClasificaciones';

  static const String clientEndpoint = '/api/client/clientes';
  static const String clientEditEndpoint = '/api/client/editClientes';

  static const String inventarioEndpoint = '/api/invent/inventarios';

  static const String cargarInventarioEndpoint =
      '/api/invent/inventarios/cargarInv';

  static const String ajusteInventarioEndpoint =
      '/api/invent/inventarios/ajuste';

  static const String movimientosInventarioEndpoint = '/api/invent/movInv';

  static const String inventarioFiltroEndpoint =
      '/api/invent/inventarios/filtro';

  static const String movimientosFiltroEndpoint = '/api/invent/movInv/filtro';

  static const String inventarioProductoEndpoint = '/api/invent/producto';

  static const String movimientosFechaEndpoint = '/api/invent/movInv';

  static const String ventaEndpoint = '/api/vent/ventas';

  static const String ventaEditEndpoint = '/api/vent/editVentas';

  static const String devolucionVentaEndpoint =
      '/api/vent/ventas/devolucion/erp';

  static const String danoVentaEndpoint = '/api/vent/ventas/dano/erp';

  static const String ventasFechaEndpoint = '/api/vent/ventas/fecha';

  static const String ventasMesEndpoint = '/api/vent/ventas/mes';

  static const String ventasFiltroEndpoint = '/api/vent/ventas/filtro';
}
