import 'package:flutter/material.dart';

class EditCliente extends StatefulWidget {
  const EditCliente({super.key});

  @override
  State<EditCliente> createState() => _EditClienteState();
}

class _EditClienteState extends State<EditCliente> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Editar cliente"),
        actions: [
          IconButton(onPressed: () {}, icon: Icon(Icons.co_present_outlined)),
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
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Nombre"),
                        suffixIcon: Icon(Icons.location_history),
                      ),
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Apellido"),
                        suffixIcon: Icon(Icons.location_history),
                      ),
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Telefono"),
                        suffixIcon: Icon(Icons.phonelink_ring_rounded),
                      ),
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Direccion"),
                        suffixIcon: Icon(Icons.display_settings_rounded),
                      ),
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Barrio/Sector"),
                        suffixIcon: Icon(Icons.other_houses_rounded),
                      ),
                    ),
                    TextFormField(
                      decoration: InputDecoration(
                        label: Text("Correo"),
                        suffixIcon: Icon(Icons.alternate_email_rounded),
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
