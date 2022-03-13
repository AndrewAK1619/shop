package com.example.shop.service;

import com.example.shop.model.dao.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemplateService {

    Template getTemplateByName(String templateName);

    Template create(Template template);

    Template update(Long id, Template template);

    Template getById(Long id);

    void deleteById(Long id);

    Page<Template> getPage(Pageable pageable);
}
