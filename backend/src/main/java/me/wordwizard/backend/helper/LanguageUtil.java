package me.wordwizard.backend.helper;

import me.wordwizard.backend.api.model.vocabulary.language.LanguageDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LanguageUtil {
    private String[] iso639Codes;
    private Locale enGbLocale = new Locale("en_GB");
    private List<LanguageDTO> languageList;

    {
        this.iso639Codes = Locale.getISOLanguages();
        Arrays.sort(iso639Codes);
        this.languageList = Stream
                .of(iso639Codes)
                .map(Locale::new)
                .map(v -> new LanguageDTO(v.getLanguage(), v.getDisplayLanguage(enGbLocale), v.getDisplayLanguage(v)))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Get locale specified by ISO 639 code.
     * <p>
     * Only {@link Locale#getISOLanguages()} language codes are supported
     *
     * @param code Language code
     * @return Locale object
     */
    public Locale getLocale(String code) {
        return Optional
                .ofNullable(code)
                .filter(v -> Arrays.binarySearch(iso639Codes, code) >= 0)
                .map(Locale::new)
                .orElse(null);
    }

    public List<LanguageDTO> getLanguageList() {
        return languageList;
    }
}
