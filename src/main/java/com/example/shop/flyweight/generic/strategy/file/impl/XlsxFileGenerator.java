package com.example.shop.flyweight.generic.strategy.file.impl;

import com.example.shop.flyweight.generic.strategy.file.FileGeneratorStrategy;
import com.example.shop.flyweight.model.FileType;
import com.example.shop.model.dao.Product;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsxFileGenerator implements FileGeneratorStrategy {

    private final ProductRepository productRepository;

    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generateFile() {
        try (Workbook workbook = WorkbookFactory.create(false)) {
            Sheet sheet = workbook.createSheet("Report");

            createHeaders(sheet);
            fillRecordsWithProductData(sheet);

            return fileToByteArray(workbook);
        } catch (IOException e) {
            log.error("Couldn't create file");
        }
        return new byte[0];
    }

    private void createHeaders(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("NAME");
        row.createCell(2).setCellValue("SERIAL NUMBER");
        row.createCell(3).setCellValue("QUANTITY");
        row.createCell(4).setCellValue("PRICE");
        row.createCell(5).setCellValue("DESCRIPTION");
    }

    private void fillRecordsWithProductData(Sheet sheet) {
        List<Product> products = productRepository.findAll();
        Row row;
        for (int i = 1; i < products.size(); i++) {
            row = sheet.createRow(i);
            Product product = products.get(i);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getSerialNumber());
            row.createCell(3).setCellValue(product.getQuantity());
            row.createCell(4).setCellValue(product.getPrice());
            row.createCell(5).setCellValue(product.getDescription());
        }
        sheet.setAutoFilter(new CellRangeAddress(0, products.size(), 0, 5));
    }

    private byte[] fileToByteArray(Workbook workbook) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
