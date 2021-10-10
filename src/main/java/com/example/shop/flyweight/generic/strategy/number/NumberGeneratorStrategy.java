package com.example.shop.flyweight.generic.strategy.number;

import com.example.shop.flyweight.generic.strategy.GenericStrategy;
import com.example.shop.flyweight.model.NumberType;

import java.util.List;

public interface NumberGeneratorStrategy<N extends Number> extends GenericStrategy<NumberType> {

    N calculate(List<N> numbers);
}
