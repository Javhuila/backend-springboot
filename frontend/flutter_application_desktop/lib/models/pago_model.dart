import 'package:flutter_application_desktop/models/enum/tipo_pago.dart';
import 'package:flutter_application_desktop/models/enum/tipo_tarjeta.dart';

class PagoModel {
  final TipoPago tipoPago;
  final double monto;
  final String? banco;
  final String? numeroReferencia;
  final TipoTarjeta? tipoTarjeta;
  final String? ultimosDigitos;

  PagoModel({
    required this.tipoPago,
    required this.monto,
    this.banco,
    this.numeroReferencia,
    this.tipoTarjeta,
    this.ultimosDigitos,
  });

  factory PagoModel.fromJson(Map<String, dynamic> json) {
    return PagoModel(
      tipoPago: tipoPagoFromString(json['tipoPago']),
      monto: (json['monto'] as num).toDouble(),
      banco: json['banco'],
      numeroReferencia: json['numeroReferencia'],
      tipoTarjeta: json['tipoTarjeta'] != null
          ? tipoTarjetaFromString(json['tipoTarjeta'])
          : null,
      ultimosDigitos: json['ultimosDigitos'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'tipoPago': tipoPago,
      'monto': monto,
      'banco': banco,
      'numeroReferencia': numeroReferencia,
      'tipoTarjeta': tipoTarjeta,
      'ultimosDigitos': ultimosDigitos,
    };
  }
}
