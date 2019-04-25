package me.wordwizard.backend.helper;

import org.junit.Before;
import org.junit.Test;

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
}
