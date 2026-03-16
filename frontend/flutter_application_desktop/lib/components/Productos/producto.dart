import 'package:flutter/material.dart';
import 'package:flutter_application_desktop/components/Productos/add_product.dart';
import 'package:flutter_application_desktop/components/Productos/edit_producto.dart';
import 'package:flutter_application_desktop/models/product.dart';
import 'package:flutter_application_desktop/services/product_service.dart';

class Producto extends StatefulWidget {
  const Producto({super.key});

  @override
  State<Producto> createState() => _ProductoState();
}

class _ProductoState extends State<Producto> {
  late Future<List<Product>> futureProducts;

  @override
  void initState() {
    super.initState();
    futureProducts = ProductService().getProducts();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Productos'),
        actions: [
          IconButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => const AddProduct()),
              );
            },
            icon: Icon(Icons.add_shopping_cart_rounded),
          ),
        ],
      ),
      body: FutureBuilder<List<Product>>(
        future: futureProducts,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No hay productos'));
          }

          final products = snapshot.data!;

          return ListView.builder(
            itemCount: products.length,
            itemBuilder: (context, index) {
              final p = products[index];

              return Card(
                child: ListTile(
                  title: Text(p.nombre),
                  subtitle: Text('Marca: ${p.marca}\nPrecio: S/ ${p.precio}'),
                  trailing: IconButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => const EditProduct(),
                        ),
                      );
                    },
                    icon: Icon(Icons.edit_note_outlined),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
