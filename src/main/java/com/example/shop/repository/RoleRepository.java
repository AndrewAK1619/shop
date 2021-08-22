package com.example.shop.repository;

import com.example.shop.model.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    // jeśli wiemy, że jeden obiekt to zwracamy Optionala
    // jeżeli więcej niż jeden to lista albo set
    // jest również opcja zwracania strumieni - ale metody muszą być oznaczone @Transactional w repo- rzadko wykorzystywane
}
