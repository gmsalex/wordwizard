package me.wordwizard.backend.model.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    @NotNull
    private String name;
    private boolean active;
}



