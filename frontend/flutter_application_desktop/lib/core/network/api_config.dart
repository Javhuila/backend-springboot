import 'dart:io' show Platform;
import 'package:flutter/foundation.dart';

class ApiConfig {
  static String get baseUrl {
    if (kIsWeb) {
      return 'http://localhost:8080';
    }

    if (Platform.isAndroid) {
      return 'http://192.168.1.104:8080';
    }

    if (Platform.isWindows || Platform.isMacOS || Platform.isLinux) {
      return 'http://localhost:8080';
    }

    // iOS o teléfono físico
    return 'http://192.168.1.104:8080'; // TU IP LOCAL
    // return 'http://10.0.2.2:8080'; TU IP LOCAL
  }

  static const String productEndpoint = '/api/product/productos';
}
