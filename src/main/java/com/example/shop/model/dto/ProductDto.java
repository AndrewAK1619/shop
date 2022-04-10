package com.example.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto extends AuditableDto {

    private Long id;
    @NotBlank
    private String name;
    @Range(min = 100_000, max = 999_999)
    private Long serialNumber;
    private int quantity;
    private double price;
    @NotBlank
    private String description;
    private String path;
}
