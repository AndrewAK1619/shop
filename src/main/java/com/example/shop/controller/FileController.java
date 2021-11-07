package com.example.shop.controller;

import com.example.shop.flyweight.generic.GenericFactory;
import com.example.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.flyweight.model.FileType;
import com.example.shop.flyweight.standard.GeneratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final GeneratorFactory generatorFactory;
    private final GenericFactory<FileType, FileGeneratorStrategy> genericFactory;

    @GetMapping
    public void getFileByStandard(@RequestParam FileType fileType) {
        generatorFactory.getStrategyByType(fileType).generateFile();
    }

    @GetMapping("generic")
    public ResponseEntity<byte[]> getFileByGeneric(@RequestParam FileType fileType) {
        byte[] files = genericFactory.getStrategyByType(fileType).generateFile();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // jaki format danych zwracamy użykownikowi
        httpHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(files.length));
        // jaka wielkość danych (info dla przeglądarki, dzięki temu przeglądarka wie ile musi ściągnąć danych (progress bar))
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + fileType.name().toLowerCase());
        // jaka nazwa pliku
        return ResponseEntity.ok().headers(httpHeaders).body(files);
    }
}
