package me.wordwizard.backend.security.auth.util;

import me.wordwizard.backend.security.auth.userdetails.principal.WWUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtil {
    /**
     * Get user principal
     *
     * @return Authenticated user's principal
     * @throws IllegalArgumentException if no authentication information is available
     */
    public WWUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (WWUserDetails) Optional.of(authentication)
                .map(Authentication::getPrincipal)
                .orElseThrow(() -> new IllegalStateException("Authentication information is not available"));
    }

    /**
     * Get authenticated user id
     *
     * @return Authenticated users's id
     * @throws IllegalArgumentException if no authentication information is available
     */
    public Long getUserId() {
        return getUserDetails().getId();
    }
}
