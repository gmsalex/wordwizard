package me.wordwizard.backend.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLanguageCodeValidatorNotNull.class)
@Documented
public @interface ValidLanguageCodeNotNull {
    String message() default "Invalid language code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
