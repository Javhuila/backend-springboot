import 'package:flutter/material.dart';
import 'package:flutter_application_desktop/components/Inventarios/add_pro_inventario.dart';

class Inventario extends StatefulWidget {
  const Inventario({super.key});

  @override
  State<Inventario> createState() => _InventarioState();
}

class _InventarioState extends State<Inventario> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Control de inventario"),
        actions: [
          IconButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => const AddProInventario(),
                ),
              );
            },
            icon: Icon(Icons.add_business_outlined),
          ),
        ],
      ),
      body: Column(
        children: [
          GestureDetector(
            onTap: () {
              //Cambiar la cantidad del stock por producto
            },
            child: Card(
              child: Row(
                children: [
                  Icon(Icons.image),
                  SizedBox(width: 10),
                  Text("Nombre del producto"),
                ],
              ),
            ),
          ),
          Wrap(
            spacing: 15,
            children: [Text("Cantidad"), Text("Vendido"), Text("Restante")],
          ),
        ],
      ),
    );
  }
}
