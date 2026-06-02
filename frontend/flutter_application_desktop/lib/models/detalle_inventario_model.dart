import 'package:flutter_application_desktop/models/enum/tipo_movimiento.dart';
import 'package:flutter_application_desktop/models/enum/tipo_operacion.dart';
import 'package:flutter_application_desktop/models/product.dart';

class DetalleInventarioModel {
  final int? idDetalleInventario;
  final Product producto;
  final int cantidad;
  final TipoMovimiento tipo;
  final DateTime? fecha;
  final String referencia;
  final TipoOperacion tipoOperacion;
  final String? observacion;
  final int stockAnterior;
  final int stockNuevo;

  DetalleInventarioModel({
    this.idDetalleInventario,
    required this.producto,
    required this.cantidad,
    required this.tipo,
    this.fecha,
    required this.referencia,
    required this.tipoOperacion,
    this.observacion,
    required this.stockAnterior,
    required this.stockNuevo,
  });

  factory DetalleInventarioModel.fromJson(Map<String, dynamic> json) {
    return DetalleInventarioModel(
      idDetalleInventario: json['idDetalleInventario'],
      producto: Product.fromJson(json['producto']),
      cantidad: json['cantidad'],
      tipo: tipoMovimientoFromString(json['tipo']),
      fecha: json['fecha'] != null ? DateTime.parse(json['fecha']) : null,
      referencia: json['referencia'] ?? '',
      tipoOperacion: tipoOperacionFromString(json['tipoOperacion']),
      observacion: json['observacion'],
      stockAnterior: json['stockAnterior'] ?? 0,
      stockNuevo: json['stockNuevo'] ?? 0,
    );
  }
}
