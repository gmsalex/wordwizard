package me.wordwizard.backend.api.model.vocabulary.entry;

import lombok.Getter;
import lombok.Setter;
import me.wordwizard.backend.model.core.MetaData;
import me.wordwizard.backend.validation.ValidLanguageCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NotNull
public class VEBaseDTO {
    @NotBlank
    @Length(min = 1, max = 512)
    private String term;
    @ValidLanguageCode
    private String language;
    @Valid
    @NotNull
    private MetaData meta;
}
