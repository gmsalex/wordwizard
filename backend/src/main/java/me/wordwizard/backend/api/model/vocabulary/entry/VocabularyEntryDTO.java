package me.wordwizard.backend.api.model.vocabulary.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NotNull
public class VocabularyEntryDTO extends VEBaseDTO {
    private long id;
}
