package com.example.shop.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class BasketDto {

    @NotNull
    private Long productId;
    @Positive
    private int quantity;
}
