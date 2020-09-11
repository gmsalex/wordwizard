package me.wordwizard.backend.api.model.vocabulary.tag;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VocabularyTagDTO {
    private Long id;
    @NotNull
    @Length(max = 64)
    private String name;
}
