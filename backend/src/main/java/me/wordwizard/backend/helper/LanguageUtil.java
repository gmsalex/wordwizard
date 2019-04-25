package me.wordwizard.backend.helper;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Component
public class LanguageUtil {
    private String[] iso639Codes;

    public LanguageUtil() {
        this.iso639Codes = Locale.getISOLanguages();
        Arrays.sort(iso639Codes);
    }

    /**
     * Get locale specified by ISO 639 code.
     * <p>
     * Only {@link Locale#getISOLanguages()} language codes are supported
     *
     * @param code Language code
     * @return Locale object
     */
    @Nullable
    public Locale getLocale(String code) {
        return Optional
                .ofNullable(code)
                .filter(v -> Arrays.binarySearch(iso639Codes, code) >= 0)
                .map(Locale::new)
                .orElse(null);
    }
}
