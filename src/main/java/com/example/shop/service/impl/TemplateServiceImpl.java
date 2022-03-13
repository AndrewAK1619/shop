package com.example.shop.service.impl;

import com.example.shop.model.dao.Template;
import com.example.shop.repository.TemplateRepository;
import com.example.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public Template getTemplateByName(String templateName) {
        return templateRepository.findByName(templateName)
                .orElseThrow(() -> new EntityNotFoundException(templateName));
    }

    @Override
    public Template create(Template template) {
        return templateRepository.save(template);
    }

    @Override
    @Transactional
    public Template update(Long id, Template template) {
        var templateDb = getById(id);
        templateDb.setName(template.getName());
        templateDb.setSubject(template.getSubject());
        templateDb.setBody(template.getBody());
        return templateDb;
    }

    @Override
    public Template getById(Long id) {
        return templateRepository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        templateRepository.deleteById(id);
    }

    @Override
    public Page<Template> getPage(Pageable pageable) {
        return templateRepository.findAll(pageable);
    }
}
