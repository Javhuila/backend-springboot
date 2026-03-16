class Product {
  final int idProduct;
  final String codigo;
  final String nombre;
  final String contenido;
  final double precio;
  final String marca;
  final String fotoUrl;

  Product({
    required this.idProduct,
    required this.codigo,
    required this.nombre,
    required this.contenido,
    required this.precio,
    required this.marca,
    required this.fotoUrl,
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
    );
  }
}
