package me.wordwizard.backend.security.auth.provider;

import me.wordwizard.backend.security.auth.userdetails.principal.WWUserDetails;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class WWDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    public WWDaoAuthenticationProvider(UserDetailsService userDetailsService) {
        this.setUserDetailsService(userDetailsService);
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        if (!(principal instanceof WWUserDetails)) {
            throw new AuthenticationServiceException(String.format("Unsupported type of principal object: '%s'", principal.getClass().getName()));
        }
        var wwDetails = (WWUserDetails) principal;
        return new UsernamePasswordAuthenticationToken(
                new WWUserPrincipal(wwDetails),
                authentication.getCredentials(),
                wwDetails.getAuthorities());
    }
}
