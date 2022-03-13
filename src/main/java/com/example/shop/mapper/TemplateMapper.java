package com.example.shop.mapper;

import com.example.shop.model.dao.Template;
import com.example.shop.model.dto.TemplateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper extends AuditableMapper<Template, TemplateDto> {

    TemplateDto daoToDto(Template dao);

    Template dtoToDao(TemplateDto dto);
}
