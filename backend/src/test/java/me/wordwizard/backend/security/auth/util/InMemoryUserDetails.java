package me.wordwizard.backend.security.auth.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDetails implements UserDetailsManager,
        UserDetailsPasswordService {
    protected final Log logger = LogFactory.getLog(getClass());
    private final Map<String, UserDetails> users = new HashMap<>();


    public InMemoryUserDetails() {
    }

    public InMemoryUserDetails(Collection<UserDetails> users) {
        for (UserDetails user : users) {
            createUser(user);
        }
    }

    public InMemoryUserDetails(UserDetails... users) {
        for (UserDetails user : users) {
            createUser(user);
        }
    }

    public void createUser(UserDetails user) {
        Assert.isTrue(!userExists(user.getUsername()), "user should not exist");
        users.put(user.getUsername().toLowerCase(), (UserDetails) user);
    }

    public void deleteUser(String username) {
        users.remove(username.toLowerCase());
    }

    public void updateUser(UserDetails user) {
        Assert.isTrue(userExists(user.getUsername()), "user should exist");
        users.put(user.getUsername().toLowerCase(), user);
    }

    public boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    public void changePassword(String oldPassword, String newPassword) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        throw new IllegalStateException("Not implemented");
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return users.get(username.toLowerCase());
    }
}
