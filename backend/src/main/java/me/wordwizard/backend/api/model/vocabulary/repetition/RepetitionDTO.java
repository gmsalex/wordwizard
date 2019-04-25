package me.wordwizard.backend.api.model.vocabulary.repetition;


import lombok.Getter;
import lombok.Setter;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularyEntryDTO;

import javax.validation.Valid;

@Getter
@Setter
public class RepetitionDTO {
    private long id;
    @Valid
    private VocabularyEntryDTO entry;
}
