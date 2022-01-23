package com.example.shop.validator;

import com.example.shop.validator.impl.JpgPngValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JpgPngValidator.class)
public @interface JpgPngValid {

    String message() default "Extension - should be JPG, JPEG or PNG";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
