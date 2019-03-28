package me.wordwizard.backend.security.auth.userdetails.principal;

import lombok.NoArgsConstructor;
import me.wordwizard.backend.model.entity.user.User;

@NoArgsConstructor
public class WWUserPrincipal {
    private User user;

    public WWUserPrincipal(WWUserDetails credential) {
        this.user = credential.getUser();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return String.valueOf(user.getId());
    }
}
