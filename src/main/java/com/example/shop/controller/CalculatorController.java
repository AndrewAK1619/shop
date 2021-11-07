package com.example.shop.controller;

import com.example.shop.flyweight.generic.GenericFactory;
import com.example.shop.flyweight.generic.strategy.number.NumberGeneratorStrategy;
import com.example.shop.flyweight.model.NumberType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final GenericFactory<NumberType, NumberGeneratorStrategy<Number>> genericFactory;

    @GetMapping
    public Number calculateByNumberType(@RequestParam NumberType numberType, @RequestParam List<Number> numbers) {
        return genericFactory.getStrategyByType(numberType).calculate(numbers);
    }
}
