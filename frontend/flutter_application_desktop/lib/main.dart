import 'package:flutter/material.dart';
import 'package:flutter_application_desktop/Layout/ini_layout.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

void main() {
  runApp(ProviderScope(child: const MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.pinkAccent),
        scaffoldBackgroundColor: Colors.pink[300],
      ),
      home: IniLayout(),
    );
  }
}
