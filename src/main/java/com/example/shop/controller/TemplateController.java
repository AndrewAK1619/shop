package com.example.shop.controller;

import com.example.shop.mapper.TemplateMapper;
import com.example.shop.model.dto.TemplateDto;
import com.example.shop.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/template")
@PreAuthorize("hasRole('ADMIN')")
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @PostMapping
    @Operation(description = "Update template", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public TemplateDto saveTemplate(@RequestBody @Valid TemplateDto templateDto) {
        return templateMapper.daoToDto(templateService.create(templateMapper.dtoToDao(templateDto)));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update template", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public TemplateDto updateUser(@PathVariable Long id, @Valid @RequestBody TemplateDto templateDto) {
        return templateMapper.daoToDto(templateService.update(id, templateMapper.dtoToDao(templateDto)));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete Template By Id", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public void deleteTemplateById(@PathVariable Long id) {
        templateService.deleteById(id);
    }

    @GetMapping
    @Operation(description = "Get Page User", security = @SecurityRequirement(name = "JWT_Shop_Security"))
    public Page<TemplateDto> getPageTemplate(@RequestParam int page, @RequestParam int size) {
        return templateService.getPage(PageRequest.of(page, size)).map(templateMapper::daoToDto);
    }
}
