package com.example.shop.flyweight.standard.strategy.impl;

import com.example.shop.flyweight.model.FileType;
import com.example.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CsvGenerator implements GeneratorStrategy {

    @Override
    public FileType getType() {
        return FileType.CSV;
    }

    @Override
    public byte[] generateFile() {
        log.info("Generate CSV file");
        return new byte[0];
    }
}
