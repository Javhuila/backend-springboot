import 'package:flutter/material.dart';
import 'package:flutter_application_desktop/components/Pedidos/pedidos.dart';
import 'package:flutter_application_desktop/components/Productos/producto.dart';

class IniLayout extends StatefulWidget {
  const IniLayout({super.key});

  @override
  State<IniLayout> createState() => _IniLayoutState();
}

class _IniLayoutState extends State<IniLayout> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Punto de Control")),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(height: 50),
            ActionButtonWidget(
              nameButton: "Pedidos",
              actionButton: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const Pedidos()),
                );
              },
            ),
            const SizedBox(height: 50),
            ActionButtonWidget(
              nameButton: "Productos",
              actionButton: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Producto()),
                );
              },
            ),
            const SizedBox(height: 50),
            ActionButtonWidget(
              nameButton: "Clientes",
              actionButton: () {
                // Navigator.push(
                //   context,
                //   MaterialPageRoute(builder: (context) => const Clientes()),
                // );
              },
            ),
            const SizedBox(height: 50),
            ActionButtonWidget(
              nameButton: "Inventario",
              actionButton: () {
                // Navigator.push(
                //   context,
                //   MaterialPageRoute(
                //     builder: (context) => const Inventario(),
                //   ),
                // );
              },
            ),
            const SizedBox(height: 50),
          ],
        ),
      ),
    );
  }
}

class ActionButtonWidget extends StatelessWidget {
  const ActionButtonWidget({
    super.key,
    required this.nameButton,
    required this.actionButton,
  });

  final String nameButton;
  final VoidCallback actionButton;

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.white54, width: 2.0),
        borderRadius: BorderRadius.circular(30),
      ),
      width: double.infinity,
      height: 70,
      child: ElevatedButton(
        onPressed: actionButton,
        child: Text(
          nameButton,
          textAlign: TextAlign.center,
          style: const TextStyle(fontSize: 20),
        ),
      ),
    );
  }
}
