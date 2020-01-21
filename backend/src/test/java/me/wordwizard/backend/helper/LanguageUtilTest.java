package me.wordwizard.backend.helper;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class LanguageUtilTest {
    private LanguageUtil languageUtil;

    @Before
    public void setUp() throws Exception {
        languageUtil = new LanguageUtil();
    }

    @Test
    public void getLocale() {
        var gbLocale = new Locale("en_GB");
        var english = languageUtil.getLocale("en");
        assertThat(english).isNotNull();
        assertThat(english.getDisplayLanguage(gbLocale)).isEqualTo("English");
    }

    @Test
    public void getUnknownLocale() {
        assertThat(languageUtil.getLocale("unknown")).isNull();
        assertThat(languageUtil.getLocale(null)).isNull();
    }

    @Test
    public void getLanguages() {
        var language = languageUtil.getLanguageList().stream().filter(v -> "fr".equals(v.getCode())).findFirst();
        assertThat(language).isPresent().get().extracting("code", "name", "nativeName").isEqualTo(Arrays.asList("fr", "French", "français"));
    }
}
