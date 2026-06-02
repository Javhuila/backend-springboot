import 'enum/estado_producto.dart';

class Product {
  final int? idProduct;
  final String? codigo;
  final String nombre;
  final String contenido;
  final double precio;
  final String marca;
  final String? fotoUrl;
  final EstadoProducto estado;
  final DateTime? fechaCreacion;
  final DateTime? fechaActualizacion;
  final int totalVendido;
  final int? categoriaId;
  final int? clasificacionId;

  Product({
    this.idProduct,
    this.codigo,
    required this.nombre,
    required this.contenido,
    required this.precio,
    required this.marca,
    this.fotoUrl,
    this.estado = EstadoProducto.ACTIVO,
    this.fechaCreacion,
    this.fechaActualizacion,
    this.totalVendido = 0,
    required this.categoriaId,
    required this.clasificacionId,
  });

  factory Product.fromJson(Map<String, dynamic> json) {
    return Product(
      idProduct: json['idProduct'],
      codigo: json['codigo'],
      nombre: json['nombre'],
      contenido: json['contenido'],
      precio: (json['precio'] as num).toDouble(),
      marca: json['marca'],
      fotoUrl: json['fotoUrl'],
      estado: estadoFromString(json['estado']),
      fechaCreacion: json['fechaCreacion'] != null
          ? DateTime.parse(json['fechaCreacion'])
          : null,
      fechaActualizacion: json['fechaActualizacion'] != null
          ? DateTime.parse(json['fechaActualizacion'])
          : null,
      totalVendido: json['totalVendido'] ?? 0,
      categoriaId: json['categoria'] != null
          ? json['categoria']['idCategoria']
          : null,
      clasificacionId: json['clasificacion'] != null
          ? json['clasificacion']['idClasificacion']
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "codigo": codigo,
      "nombre": nombre,
      "contenido": contenido,
      "precio": precio,
      "marca": marca,
      "fotoUrl": fotoUrl,
      "fechaCreacion": fechaCreacion,
      "fechaActualizacion": fechaActualizacion,
      "estado": estadoToString(estado),
      "categoriaId": categoriaId,
      "clasificacionId": clasificacionId,
    };
  }

  Product copyWith({EstadoProducto? estado, int? totalVendido}) {
    return Product(
      idProduct: idProduct,
      codigo: codigo,
      nombre: nombre,
      contenido: contenido,
      precio: precio,
      marca: marca,
      fotoUrl: fotoUrl,
      estado: estado ?? this.estado,
      fechaCreacion: fechaCreacion,
      fechaActualizacion: fechaActualizacion,
      totalVendido: totalVendido ?? this.totalVendido,
      categoriaId: categoriaId,
      clasificacionId: clasificacionId,
    );
  }
}
