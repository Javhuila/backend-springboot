import 'package:dio/dio.dart';
import '../core/network/dio_client.dart';
import '../core/network/api_config.dart';
import '../models/product.dart';

class ProductService {
  final Dio _dio = DioClient.instance;

  Future<List<Product>> getProducts() async {
    try {
      final Response response = await _dio.get(ApiConfig.productEndpoint);

      final List data = response.data;

      return data.map((e) => Product.fromJson(e)).toList();
    } on DioException catch (e) {
      throw Exception(e.response?.data ?? 'Error al obtener productos');
    }
  }
}
