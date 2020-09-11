package me.wordwizard.backend.api.model.vocabulary.entry;

import lombok.Getter;
import lombok.Setter;
import me.wordwizard.backend.api.model.vocabulary.tag.VocabularyTagDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class VECreateDTO {
    @Valid
    private VEBaseDTO entry;
    @Valid
    @NotNull
    private List<VocabularyTagDTO> tagList = Collections.emptyList();
}
