package com.example.shop.controller;

import com.example.shop.flyweight.model.FileType;
import com.example.shop.flyweight.standard.GeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final GeneratorFactory generatorFactory;

    @GetMapping
    public void getFileByStandard(@RequestParam FileType fileType) {
        generatorFactory.getStrategyByType(fileType).generateFile();
    }
}
