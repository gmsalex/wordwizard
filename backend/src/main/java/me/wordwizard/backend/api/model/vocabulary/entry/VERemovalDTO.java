package me.wordwizard.backend.api.model.vocabulary.entry;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class VERemovalDTO {
    @NotNull
    @Size(min = 1, max = 100)
    private Set<Long> veIds;
}
