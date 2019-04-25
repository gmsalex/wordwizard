package me.wordwizard.backend.model.entity.user;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @Basic(optional = false)
    private String email;
    @NotNull
    @Basic(optional = false)
    private String password;
}
