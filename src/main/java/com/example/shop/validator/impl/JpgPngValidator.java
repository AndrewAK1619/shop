package com.example.shop.validator.impl;

import com.example.shop.validator.JpgPngValid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JpgPngValidator implements ConstraintValidator<JpgPngValid, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename() == null)
            return false;

        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename()).toUpperCase();
        return extension.equals("JPG") || extension.equals("JPEG") || extension.equals("PNG");


//        if (!multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
//            String fileName = multipartFile.getOriginalFilename();
//            return fileName.endsWith(".JPG") || fileName.endsWith(".JPEG") || fileName.endsWith(".PNG");
//        } else {
//            return false;
//        }
    }
}
