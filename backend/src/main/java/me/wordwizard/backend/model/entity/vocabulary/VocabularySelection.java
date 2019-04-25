package me.wordwizard.backend.model.entity.vocabulary;

import lombok.Getter;
import lombok.Setter;
import me.wordwizard.backend.model.entity.user.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class VocabularySelection {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Length(min = 1, max = 64)
    @Column(length = 64)
    private String name;
    @OneToMany(mappedBy = "selection")
    private List<Repetition> repetitions;
    @NotNull
    @ManyToOne(optional = false)
    private User user;
}
