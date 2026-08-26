package com.example.taskmanager.security;

import com.example.taskmanager.model.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Adapts an application {@link User} to Spring Security's user contract.
 *
 * @param user persisted user represented by this security principal
 */
public record CustomUserDetails(User user) implements UserDetails {
    /**
     * Returns the authority derived from the user's role.
     *
     * @return the user's granted authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    /**
     * Returns the stored encoded password for authentication checks.
     *
     * @return encoded password
     */
    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the user's email as the security username.
     *
     * @return user email
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
