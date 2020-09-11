package me.wordwizard.backend.model.entity.vocabulary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    @ManyToOne
    private VocabularyTag tag;

    public Repetition(@NotNull VocabularyEntry entry) {
        this.entry = entry;
    }

    public Repetition(@NotNull VocabularyEntry entry, VocabularyTag tag) {
        this.entry = entry;
        this.tag = tag;
    }
}
