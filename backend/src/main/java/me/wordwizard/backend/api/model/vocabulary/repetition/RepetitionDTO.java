package me.wordwizard.backend.api.model.vocabulary.repetition;


import lombok.Getter;
import lombok.Setter;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularyEntryDTO;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Getter
@Setter
public class RepetitionDTO {
    private long id;
    private LocalDateTime created;
    @Valid
    private VocabularyEntryDTO entry;
}
