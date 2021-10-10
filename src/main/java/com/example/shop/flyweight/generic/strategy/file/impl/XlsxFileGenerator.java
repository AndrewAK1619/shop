package com.example.shop.flyweight.generic.strategy.file.impl;

import com.example.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.flyweight.model.FileType;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsxFileGenerator implements FileGeneratorStrategy {

    private final ProductRepository productRepository;

    @Override
    public FileType getType() {
        return FileType.XLSX;
    }

    @Override
    public byte[] generateFile() {
        log.info("Generate XLSX");
        return new byte[0];
    }
}
