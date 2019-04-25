package me.wordwizard.backend.api.model.vocabulary.entry;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class VEAssociateDTO {
    private Set<Long> veIds;
}
