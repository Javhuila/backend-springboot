import 'package:flutter/material.dart';

class AddProInventario extends StatefulWidget {
  const AddProInventario({super.key});

  @override
  State<AddProInventario> createState() => _AddProInventarioState();
}

class _AddProInventarioState extends State<AddProInventario> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Control de inventario"),
        actions: [
          IconButton(
            onPressed: () {
              //Agregar productos seleccionados
            },
            icon: Icon(Icons.check_circle),
          ),
        ],
      ),
      body: Padding(
        padding: EdgeInsetsGeometry.all(8),
        child: Column(
          children: [
            ListTile(
              leading: Icon(Icons.image),
              title: Text("nombre"),
              trailing: Icon(Icons.radio_button_unchecked_outlined),
              onTap: () {
                //Funcion para seleccionar el prodcto
              },
            ),
          ],
        ),
      ),
    );
  }
}
