package me.wordwizard.backend.validation;

import me.wordwizard.backend.helper.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class ValidLanguageCodeValidatorNotNull implements ConstraintValidator<ValidLanguageCodeNotNull, String> {
    private final LanguageUtil languageUtil;

    @Autowired
    public ValidLanguageCodeValidatorNotNull(LanguageUtil languageUtil) {
        this.languageUtil = languageUtil;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value).map(v -> languageUtil.getLocale(v) != null).orElse(false);
    }
}
