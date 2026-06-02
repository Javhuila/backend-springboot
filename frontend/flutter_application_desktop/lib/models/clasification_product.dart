class ClasificationProduct {
  final int? idClasificacion;
  final String nombre;

  ClasificationProduct({this.idClasificacion, required this.nombre});

  factory ClasificationProduct.fromJson(Map<String, dynamic> json) {
    return ClasificationProduct(
      idClasificacion: json['idClasificacion'],
      nombre: json['nombre'],
    );
  }

  Map<String, dynamic> toJson() {
    return {"nombre": nombre};
  }
}
