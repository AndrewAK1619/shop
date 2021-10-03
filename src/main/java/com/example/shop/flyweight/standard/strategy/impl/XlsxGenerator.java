package com.example.shop.flyweight.standard.strategy.impl;

import com.example.shop.flyweight.model.FileType;
import com.example.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class XlsxGenerator implements GeneratorStrategy {

    @Override
    public FileType getType() {
        return FileType.XLSX;
    }

    @Override
    public byte[] generateFile() {
        log.info("Generate XLSX file");
        return new byte[0];
    }
}
