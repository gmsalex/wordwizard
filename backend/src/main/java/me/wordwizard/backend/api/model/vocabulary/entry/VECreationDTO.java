package me.wordwizard.backend.api.model.vocabulary.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
public class VECreationDTO {
    private long selectionId;
    @Valid
    private VEBaseDTO entry;
}
