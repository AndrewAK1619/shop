package com.example.shop.flyweight.generic.strategy.file.impl;

import com.example.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.flyweight.model.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CsvFileGenerator implements FileGeneratorStrategy {

    @Override
    public FileType getType() {
        return FileType.CSV;
    }

    @Override
    public byte[] generateFile() {
        log.info("Generate CSV");
        return new byte[0];
    }
}
