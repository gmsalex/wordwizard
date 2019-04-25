package me.wordwizard.backend.api.model.vocabulary.selection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NotNull
@NoArgsConstructor
public class VSCreationDTO {
    @NotBlank
    @Pattern(regexp = "^(\\p{L}\\p{M}*+|\\d|\\s){1,64}$")
    private String name;

    public VSCreationDTO(String name) {
        this.name = name;
    }
}
