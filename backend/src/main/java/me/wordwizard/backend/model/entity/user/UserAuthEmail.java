package me.wordwizard.backend.model.entity.user;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class UserAuthEmail {
    @Id
    private Long id;
    @OneToOne(optional = false)
    @MapsId
    private User user;
    @Email
    private String email;
    @NotNull
    private String password;
}
