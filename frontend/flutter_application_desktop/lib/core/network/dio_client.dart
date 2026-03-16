import 'package:dio/dio.dart';
import 'api_config.dart';

class DioClient {
  static final Dio _dio = Dio(
    BaseOptions(
      baseUrl: ApiConfig.baseUrl,
      connectTimeout: const Duration(seconds: 10),
      receiveTimeout: const Duration(seconds: 10),
      headers: {'Content-Type': 'application/json'},
    ),
  )..interceptors.add(LogInterceptor(requestBody: true, responseBody: true));

  static Dio get instance => _dio;
}
