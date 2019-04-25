package me.wordwizard.backend.model.entity.vocabulary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Repetition {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @ManyToOne(optional = false)
    private VocabularyEntry entry;
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private VocabularySelection selection;

    public Repetition(@NotNull VocabularyEntry entry, @NotNull VocabularySelection selection) {
        this.entry = entry;
        this.selection = selection;
    }
}
