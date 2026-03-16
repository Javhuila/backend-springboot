import 'package:flutter/material.dart';

class AddProduct extends StatefulWidget {
  const AddProduct({super.key});

  @override
  State<AddProduct> createState() => _AddProductState();
}

class _AddProductState extends State<AddProduct> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Agregar producto"),
        actions: [
          IconButton(
            onPressed: () {},
            icon: Icon(Icons.add_shopping_cart_rounded),
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Form(
          child: Row(
            children: [
              Expanded(
                flex: 1,
                child: Column(
                  spacing: 5,
                  children: [
                    DropdownButtonFormField<String>(
                      decoration: const InputDecoration(
                        labelText: 'Categoria',
                        border: OutlineInputBorder(),
                        suffixIcon: Icon(Icons.category_rounded),
                      ),
                      items: const [
                        DropdownMenuItem(
                          value: 'producto1',
                          child: Text('Categoria 1'),
                        ),
                        DropdownMenuItem(
                          value: 'producto2',
                          child: Text('Categoria 2'),
                        ),
                      ],
                      onChanged: (value) {},
                    ),
                    DropdownButtonFormField<String>(
                      decoration: const InputDecoration(
                        labelText: 'Presentacion',
                        border: OutlineInputBorder(),
                        suffixIcon: Icon(Icons.view_in_ar_rounded),
                      ),
                      items: const [
                        DropdownMenuItem(
                          value: 'producto1',
                          child: Text('clasificacion 1'),
                        ),
                        DropdownMenuItem(
                          value: 'producto2',
                          child: Text('clasificacion 2'),
                        ),
                      ],
                      onChanged: (value) {},
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Nombre"),
                        suffixIcon: Icon(Icons.edit_rounded),
                      ),
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Precio"),
                        suffixIcon: Icon(Icons.attach_money_rounded),
                      ),
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Marca"),
                        suffixIcon: Icon(Icons.app_registration_sharp),
                      ),
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Contenido"),
                        suffixIcon: Icon(Icons.scale_rounded),
                      ),
                    ),
                    SizedBox(height: 20),
                  ],
                ),
              ),
              Expanded(
                flex: 2,
                child: Column(
                  children: [
                    Container(
                      width: 250,
                      height: 250,
                      color: Colors.white,
                      margin: EdgeInsets.all(3),
                      child: Center(child: Icon(Icons.add_a_photo_rounded)),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
