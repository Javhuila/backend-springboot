import 'package:flutter_application_desktop/models/product.dart';

class DetalleVentaModel {
  final Product producto;
  final String? nombreProducto;
  final int cantidad;
  final double? precioUnitario;
  final double? subtotal;
  final bool? esBonificacion;
  final String? observacion;

  DetalleVentaModel({
    required this.producto,
    this.nombreProducto,
    required this.cantidad,
    this.precioUnitario,
    this.subtotal,
    this.esBonificacion,
    this.observacion,
  });

  factory DetalleVentaModel.fromJson(Map<String, dynamic> json) {
    return DetalleVentaModel(
      producto: Product.fromJson(json['product']),
      nombreProducto: json['nombreProducto'],
      cantidad: json['cantidad'],
      precioUnitario: (json['precioUnitario'] as num?)?.toDouble(),
      subtotal: (json['subtotal'] as num?)?.toDouble(),
      esBonificacion: json['esBonificacion'],
      observacion: json['observacion'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'product': producto,
      'cantidad': cantidad,
      'observacion': observacion,
    };
  }
}
