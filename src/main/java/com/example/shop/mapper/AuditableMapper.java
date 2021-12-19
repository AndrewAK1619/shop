package com.example.shop.mapper;

import com.example.shop.model.dao.Auditable;
import com.example.shop.model.dto.AuditableDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import static com.example.shop.security.SecurityUtils.hasRole;

public interface AuditableMapper<DAO extends Auditable, DTO extends AuditableDto> {

    @AfterMapping
    default void auditableFields(DAO dao, @MappingTarget DTO.AuditableDtoBuilder<?, ?> dto) {
        if (!hasRole("ROLE_ADMIN")) {
            dto.createdBy(null);
            dto.createdDate(null);
            dto.lastModifiedBy(null);
            dto.lastModifiedDate(null);
        }
    }
}
