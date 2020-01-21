package me.wordwizard.backend.api.model.vocabulary.language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDTO {
    private String code;
    private String name;
    private String nativeName;
}
