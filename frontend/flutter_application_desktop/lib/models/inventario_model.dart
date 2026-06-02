import 'package:flutter_application_desktop/models/product.dart';

class InventarioModel {
  final int? idInventario;
  final Product producto;
  final int stockActual;
  final DateTime? fechaActualizacion;
  final int? version;

  InventarioModel({
    this.idInventario,
    required this.producto,
    required this.stockActual,
    this.fechaActualizacion,
    this.version,
  });

  factory InventarioModel.fromJson(Map<String, dynamic> json) {
    return InventarioModel(
      idInventario: json['idInventario'],
      producto: Product.fromJson(json['producto']),
      stockActual: json['stockActual'] ?? 0,
      fechaActualizacion: json['fechaActualizacion'] != null
          ? DateTime.parse(json['fechaActualizacion'])
          : null,
      version: json['version'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'idInventario': idInventario,
      'producto': producto.toJson(),
      'stockActual': stockActual,
      'fechaActualizacion': fechaActualizacion?.toIso8601String(),
      'version': version,
    };
  }
}
