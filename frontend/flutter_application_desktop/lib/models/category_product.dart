class CategoryProduct {
  final int? idCategoria;
  final String nombre;
  final String? fotoUrl;

  CategoryProduct({this.idCategoria, required this.nombre, this.fotoUrl});

  factory CategoryProduct.fromJson(Map<String, dynamic> json) {
    return CategoryProduct(
      idCategoria: json['idCategoria'],
      nombre: json['nombre'],
      fotoUrl: json['fotoUrl'],
    );
  }

  Map<String, dynamic> toJson() {
    return {"nombre": nombre, "fotoUrl": fotoUrl};
  }
}
