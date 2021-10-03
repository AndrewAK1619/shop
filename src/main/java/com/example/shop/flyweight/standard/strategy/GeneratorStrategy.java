package com.example.shop.flyweight.standard.strategy;

import com.example.shop.flyweight.model.FileType;

public interface GeneratorStrategy {

    FileType getType();

    byte[] generateFile();
}
