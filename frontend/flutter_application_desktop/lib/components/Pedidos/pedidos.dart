import 'package:flutter/material.dart';

class Pedidos extends StatefulWidget {
  const Pedidos({super.key});

  @override
  State<Pedidos> createState() => _PedidosState();
}

class ProductoPedido {
  String nombre;
  int cantidad;
  double precio;

  ProductoPedido(this.nombre, this.cantidad, this.precio);
}

class _PedidosState extends State<Pedidos> {
  List<ProductoPedido> productos = [
    ProductoPedido("Producto 1", 1, 2000),
    ProductoPedido("Producto 2", 2, 3500),
  ];

  void aumentarCantidad(int index) {
    setState(() {
      productos[index].cantidad++;
    });
  }

  void disminuirCantidad(int index) {
    setState(() {
      if (productos[index].cantidad > 1) {
        productos[index].cantidad--;
      }
    });
  }

  void eliminarProducto(int index) {
    setState(() {
      productos.removeAt(index);
    });
  }

  double totalCompra() {
    double total = 0;
    for (var p in productos) {
      total += p.cantidad * p.precio;
    }
    return total;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Registro de compra"),
        elevation: 10,
        actions: [
          IconButton(onPressed: () {}, icon: Icon(Icons.list_alt_rounded)),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Row(
          children: [
            Expanded(
              flex: 1,
              child: Card(
                elevation: 4,
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          const Text(
                            "Selección de la compra",
                            style: TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                            ),
                          ),

                          const SizedBox(width: 10),

                          IconButton(
                            icon: const Icon(Icons.check_circle_outline),
                            onPressed: () {},
                          ),
                        ],
                      ),

                      const SizedBox(height: 20),

                      DropdownButtonFormField<String>(
                        decoration: const InputDecoration(
                          labelText: 'Clientes',
                          border: OutlineInputBorder(),
                        ),
                        items: const [
                          DropdownMenuItem(
                            value: 'clienteNormal',
                            child: Text('Cliente regular'),
                          ),
                          DropdownMenuItem(
                            value: 'clienteRegistrado',
                            child: Text('Nombre Cliente'),
                          ),
                        ],
                        onChanged: (value) {},
                      ),

                      const SizedBox(height: 20),

                      DropdownButtonFormField<String>(
                        decoration: const InputDecoration(
                          labelText: 'Producto',
                          border: OutlineInputBorder(),
                        ),
                        items: const [
                          DropdownMenuItem(
                            value: 'producto1',
                            child: Text('Producto 1'),
                          ),
                          DropdownMenuItem(
                            value: 'producto2',
                            child: Text('Producto 2'),
                          ),
                        ],
                        onChanged: (value) {},
                      ),
                      const SizedBox(height: 20),

                      DropdownButtonFormField<String>(
                        decoration: const InputDecoration(
                          labelText: 'Pago',
                          border: OutlineInputBorder(),
                        ),
                        items: const [
                          DropdownMenuItem(
                            value: 'inmediato',
                            child: Text('Pago inmediato'),
                          ),
                          DropdownMenuItem(
                            value: 'fianza',
                            child: Text('Pago afianzado'),
                          ),
                        ],
                        onChanged: (value) {},
                      ),
                    ],
                  ),
                ),
              ),
            ),
            Expanded(
              flex: 2,
              child: Column(
                children: [
                  const Text(
                    "Productos de la compra",
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                  ),

                  const SizedBox(height: 10),

                  /// LISTA DE PRODUCTOS
                  Expanded(
                    child: Card(
                      elevation: 4,
                      child: SingleChildScrollView(
                        child: DataTable(
                          columns: const [
                            DataColumn(label: Text("Producto")),

                            DataColumn(label: Text("Cantidad")),

                            DataColumn(label: Text("Precio")),

                            DataColumn(label: Text("Acciones")),
                          ],

                          rows: List.generate(productos.length, (index) {
                            final producto = productos[index];

                            return DataRow(
                              cells: [
                                DataCell(Text(producto.nombre)),

                                DataCell(Text(producto.cantidad.toString())),

                                DataCell(
                                  Text(
                                    "\$ ${(producto.precio * producto.cantidad).toStringAsFixed(0)}",
                                  ),
                                ),

                                DataCell(
                                  Row(
                                    children: [
                                      IconButton(
                                        icon: const Icon(Icons.remove_circle),
                                        onPressed: () =>
                                            disminuirCantidad(index),
                                      ),

                                      IconButton(
                                        icon: const Icon(Icons.add_circle),
                                        onPressed: () =>
                                            aumentarCantidad(index),
                                      ),

                                      IconButton(
                                        icon: const Icon(
                                          Icons.delete,
                                          color: Colors.red,
                                        ),
                                        onPressed: () =>
                                            eliminarProducto(index),
                                      ),
                                    ],
                                  ),
                                ),
                              ],
                            );
                          }),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(height: 10),
                  Text(
                    "Total: \$ ${totalCompra().toStringAsFixed(0)}",
                    style: const TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 10),
                  SizedBox(
                    width: 200,
                    child: ElevatedButton.icon(
                      onPressed: () {
                        // Guardar compra
                      },
                      label: Text("Guardar"),
                      icon: Icon(Icons.data_saver_on_rounded),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
