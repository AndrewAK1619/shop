package com.example.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDto {

    private Long id;
    @NotBlank
    private String name;
    @Size(min = 100_000, max = 999_999)
    private Long serialNumber;
    private int quantity;
    private double price;
    @NotBlank
    private String description;
}
