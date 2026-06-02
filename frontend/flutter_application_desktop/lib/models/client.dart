class Client {
  final int? idCliente;
  final String? nombreCompleto;
  final String nombre;
  final String apellido;
  final int? cedula;
  final String? correo;
  final String? telefono;
  final String? barrio;
  final String? direccion;

  Client({
    this.idCliente,
    this.nombreCompleto,
    required this.nombre,
    required this.apellido,
    this.cedula,
    this.correo,
    this.telefono,
    this.barrio,
    this.direccion,
  });

  factory Client.fromJson(Map<String, dynamic> json) {
    return Client(
      idCliente: json['idCliente'],
      nombreCompleto: json['nombreCompleto'],
      nombre: json['nombre'],
      apellido: json['apellido'],
      cedula: json['cedula'],
      correo: json['correo'],
      telefono: json['telefono'],
      barrio: json['barrio'],
      direccion: json['direccion'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "nombreCompleto": nombreCompleto,
      "nombre": nombre,
      "apellido": apellido,
      "cedula": cedula,
      "correo": correo,
      "telefono": telefono,
      "barrio": barrio,
      "direccion": direccion,
    };
  }
}
