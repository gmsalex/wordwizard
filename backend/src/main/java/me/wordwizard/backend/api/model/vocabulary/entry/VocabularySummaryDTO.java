package me.wordwizard.backend.api.model.vocabulary.entry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.wordwizard.backend.api.model.vocabulary.tag.VocabularyTagDTO;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularySummaryDTO {
    private List<VocabularyEntryDTO> veList = Collections.emptyList();
    private List<VocabularyTagDTO> tagList = Collections.emptyList();
}
