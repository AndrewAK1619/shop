package com.example.shop.flyweight.generic.strategy.file;

import com.example.shop.flyweight.generic.strategy.GenericStrategy;
import com.example.shop.flyweight.model.FileType;

public interface FileGeneratorStrategy extends GenericStrategy<FileType> {

    byte[] generateFile();
}
