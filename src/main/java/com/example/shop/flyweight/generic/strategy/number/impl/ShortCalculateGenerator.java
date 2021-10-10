package com.example.shop.flyweight.generic.strategy.number.impl;

import com.example.shop.flyweight.generic.strategy.number.NumberGeneratorStrategy;
import com.example.shop.flyweight.model.NumberType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ShortCalculateGenerator implements NumberGeneratorStrategy<Number> {

    @Override
    public NumberType getType() {
        return NumberType.SHORT;
    }

    @Override
    public Number calculate(List<Number> numbers) {
        log.info("Calculate SHORT");
        return numbers.stream()
                .mapToInt(Number::shortValue)
                .sum();
    }
}
