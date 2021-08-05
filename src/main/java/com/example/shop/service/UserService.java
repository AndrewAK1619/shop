package com.example.shop.service;

import com.example.shop.model.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User create(User user);

    User update(Long id, User user);

    User getById(Long id);

    void deleteById(Long id);

    Page<User> getPage(Pageable pageable);
}
