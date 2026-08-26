package com.example.taskmanager.security;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads application users for Spring Security authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /** Repository used to look up users by email. */
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads the user whose email is used as the security username.
     *
     * @param username email supplied by Spring Security
     * @return Spring Security details backed by the application user
     * @throws UsernameNotFoundException when no matching user exists
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Resolve the submitted login name to the persisted user account.
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email "+ username + " not found"));

        // Adapt the application entity to Spring Security's UserDetails contract.
        return new CustomUserDetails(user);
    }
}
