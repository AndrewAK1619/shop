package com.example.shop.validator;

import com.example.shop.validator.impl.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented // mówi, że tworzona adnotacja będzie adnotacją
@Target(ElementType.TYPE) // oznacza, że będzie mogła być używana nad klasą
// FIELD, METHOD, TYPE_PARAMETER - dla parametrów funkcji
@Retention(RetentionPolicy.RUNTIME) // oznacza, że w trakcie działania kodu będzie działała
@Constraint(validatedBy = PasswordValidator.class) // podłączenie naszej klasy walidatora do adnotacji
public @interface PasswordValid {

    String message() default "Password - should be the same"; // komunikat błędu

    Class<?>[] groups() default {}; // możemy włączać i wyłączać adnotacje dla kolejnych metod

    Class<? extends Payload>[] payload() default {};
}
