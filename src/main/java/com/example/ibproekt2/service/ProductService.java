package com.example.ibproekt2.service;

import com.example.ibproekt2.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    void deleteById(long id);

    Product saveProduct(Product product);
}
