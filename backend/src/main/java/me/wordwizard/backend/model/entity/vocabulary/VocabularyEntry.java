package me.wordwizard.backend.model.entity.vocabulary;

import lombok.Getter;
import lombok.Setter;
import me.wordwizard.backend.model.core.MetaData;
import me.wordwizard.backend.model.entity.user.User;
import me.wordwizard.backend.validation.ValidLanguageCode;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class VocabularyEntry {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Length(min = 1, max = 512)
    @Basic(optional = false)
    @Column(columnDefinition = "text")
    private String term;
    @ValidLanguageCode
    @Column(length = 3)
    private String language;
    @OneToMany(mappedBy = "entry", cascade = CascadeType.REMOVE)
    private List<Repetition> repetitions;
    @NotNull
    @Valid
    @Type(type = "jsonb")
    @Basic(optional = false)
    @Column(columnDefinition = "jsonb")
    private MetaData meta;
    @NotNull
    @ManyToOne(optional = false)
    private User user;
    @NotNull
    @Basic(optional = false)
    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        this.created = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
    }
}
