package me.wordwizard.backend.security.auth.userdetails;

import me.wordwizard.backend.security.auth.userdetails.principal.WWUserEmailPrincipal;
import me.wordwizard.backend.service.repository.UserWithEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JpaEmailBasedUserDetailsService implements UserDetailsService {
    private UserWithEmailRepository repository;

    @Autowired
    public JpaEmailBasedUserDetailsService(UserWithEmailRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByEmail(username)
                .map(WWUserEmailPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Can't find user '%s'", username)));
    }
}

