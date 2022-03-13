package com.example.shop.repository;

import com.example.shop.model.dao.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long>, RevisionRepository<Template, Long, Integer> {
    Optional<Template> findByName(String templateName);
}
