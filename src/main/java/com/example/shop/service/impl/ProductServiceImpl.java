package com.example.shop.service.impl;

import com.example.shop.config.properties.FilePropertiesConfig;
import com.example.shop.model.dao.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FilePropertiesConfig filePropertiesConfig;

    @Override
    @Transactional
    public Product create(Product product, MultipartFile file) {
        Product savedProduct = productRepository.save(product);

        Path path = Paths.get(filePropertiesConfig.getProduct(),
                savedProduct.getId() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        try {
            Files.copy(file.getInputStream(), path);
            String oldPath = savedProduct.getPath();
            savedProduct.setPath(path.toString());
            if (!savedProduct.getPath().equals(oldPath)) {
                Files.delete(Paths.get(oldPath));
            }
        } catch (Exception e) {
            log.error("Error during product saving file", e);
        }
        return savedProduct;
    }

    @Override
    @Transactional
    public Product update(Long id, Product product, MultipartFile file) {
        var productDb = getById(id);
        productDb.setName(product.getName());
        productDb.setSerialNumber(product.getSerialNumber());
        productDb.setDescription(product.getDescription());

        if (file != null) {
            Path path = Paths.get(filePropertiesConfig.getProduct(),
                    id + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
            try {
                Files.copy(file.getInputStream(), path);
                String oldPath = productDb.getPath();
                productDb.setPath(path.toString());
                if (!productDb.getPath().equals(oldPath)) {
                    Files.delete(Paths.get(oldPath));
                }
            } catch (Exception e) {
                log.error("Error during product saving file", e);
            }
        }
        return productDb;
    }

    @Override
    public Product getById(Long id) {
        log.info("Product none in cache {}", id);
        return productRepository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
