package com.example.shop.repository;

import com.example.shop.model.dao.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findByProductIdAndUserId(Long productId, Long userId);

    void deleteByProductIdAndUserId(Long productId, Long userId);

    void deleteByUserId(Long userId);
}
