import 'package:flutter_application_desktop/models/client.dart';

import 'detalle_venta_model.dart';
import 'pago_model.dart';

class VentaModel {
  final int? idVenta;
  final String? codigoVenta;
  final Client? cliente;
  final String? fechaVenta;
  final List<PagoModel> pagos;
  final double? totalVenta;
  final double? cambio;
  final int? totalItems;
  final String? estado;
  final String? observacion;
  final List<DetalleVentaModel> detalles;

  final int? idCliente;
  final bool? generarDeuda;

  VentaModel({
    this.idVenta,
    this.codigoVenta,
    this.cliente,
    this.fechaVenta,
    required this.pagos,
    this.totalVenta,
    this.cambio,
    this.totalItems,
    this.estado,
    this.observacion,
    required this.detalles,
    this.idCliente,
    this.generarDeuda,
  });

  factory VentaModel.fromJson(Map<String, dynamic> json) {
    return VentaModel(
      idVenta: json['idVenta'],
      codigoVenta: json['codigoVenta'],
      cliente: json['cliente'] != null
          ? Client.fromJson(json['cliente'])
          : null,
      fechaVenta: json['fechaVenta'],
      totalVenta: json['totalVenta']?.toDouble(),
      cambio: json['cambio']?.toDouble(),
      totalItems: json['totalItems'],
      estado: json['estado'],
      observacion: json['observacion'],
      pagos:
          (json['pagos'] as List<dynamic>?)
              ?.map((e) => PagoModel.fromJson(e))
              .toList() ??
          [],
      detalles:
          (json['detalles'] as List<dynamic>?)
              ?.map((e) => DetalleVentaModel.fromJson(e))
              .toList() ??
          [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'idCliente': idCliente,
      'observacion': observacion,
      'generarDeuda': generarDeuda,
      'pagos': pagos.map((e) => e.toJson()).toList(),
      'detalles': detalles.map((e) => e.toJson()).toList(),
    };
  }
}
