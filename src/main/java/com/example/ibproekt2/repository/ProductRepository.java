package com.example.ibproekt2.repository;

import com.example.ibproekt2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
