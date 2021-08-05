package com.example.shop.repository;

import com.example.shop.model.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
