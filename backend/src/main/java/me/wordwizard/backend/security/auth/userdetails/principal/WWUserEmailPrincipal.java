package me.wordwizard.backend.security.auth.userdetails.principal;

import lombok.NoArgsConstructor;
import me.wordwizard.backend.model.entity.user.User;
import me.wordwizard.backend.model.entity.user.UserAuthEmail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor
public class WWUserEmailPrincipal implements WWUserDetails {
    private String email;
    private String password;
    private User user;

    public WWUserEmailPrincipal(UserAuthEmail credential) {
        this.email = credential.getEmail();
        this.password = credential.getPassword();
        this.user = credential.getUser();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    @Override
    public User getUser() {
        return user;
    }
}
