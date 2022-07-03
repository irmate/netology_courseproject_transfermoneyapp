package ru.netology.TransferMoneyApp.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidDataCheckerImpl.class)
@Documented
public @interface ValidDataChecker {
    String message() default "{Data.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}