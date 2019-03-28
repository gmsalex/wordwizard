package me.wordwizard.backend.security.auth.userdetails.principal;

import me.wordwizard.backend.model.entity.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface WWUserDetails extends UserDetails {
    User getUser();
}
