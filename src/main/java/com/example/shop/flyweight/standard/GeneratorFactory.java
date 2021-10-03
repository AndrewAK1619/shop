package com.example.shop.flyweight.standard;

import com.example.shop.flyweight.model.FileType;
import com.example.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GeneratorFactory {

    private final List<GeneratorStrategy> strategyList;
    private Map<FileType, GeneratorStrategy> strategyMap;

    // wywoła się zaraz po konstruktorze.
    // Chcemy to zrobić, żeby zainicjalizowwać naszą mapę na podstawie wstrzykniętej listy
    @PostConstruct
    void init() {
        strategyMap = strategyList.stream()
                .collect(Collectors.toMap(GeneratorStrategy::getType, Function.identity()));
    }

    public GeneratorStrategy getStrategyByType(FileType fileType) {
        return strategyMap.get(fileType);
    }
}
