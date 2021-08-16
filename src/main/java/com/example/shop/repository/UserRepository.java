package com.example.shop.repository;

import com.example.shop.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

// RevisionRepository - dodany aby móc pobierać z tabel audytowych <nasza encja, typ Id, numer rewizji>
// daje nam dodatkowe metody w tym celu
public interface UserRepository extends JpaRepository<User, Long>, RevisionRepository<User, Long, Integer> {

}
