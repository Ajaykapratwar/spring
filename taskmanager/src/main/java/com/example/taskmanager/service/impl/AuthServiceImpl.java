package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.RegLoginRequest;
import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.enums.Role;
import com.example.taskmanager.exception.BadRequestException;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implements user registration and session-based authentication.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // Repository used to find existing users and save newly registered users.
    private final UserRepository userRepository;

    // BCrypt encoder used to store passwords securely instead of plain text.
    private final BCryptPasswordEncoder passwordEncoder;

    // Spring Security component that verifies the supplied login credentials.
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user.
     *
     * Flow:
     * 1. Reject the request when the email is already registered.
     * 2. Create a user and encrypt the password.
     * 3. Assign ADMIN only when ADMIN is explicitly requested; otherwise use USER.
     * 4. Save the user and return only safe user details.
     */
    @Override
    public ApiResponse<?> register(RegLoginRequest regRequest) {
        // An email must be unique, so stop before creating a duplicate user.
        if (userRepository.findByEmail(regRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email Already Exist");
        }

        // Create the entity that will be stored in the database.
        User user = new User();
        // Store the requested email on the new user.
        user.setEmail(regRequest.getEmail());
        // Encrypt the password before saving it; never store the plain password.
        user.setPassword(passwordEncoder.encode(regRequest.getPassword()));

        // Allow the ADMIN role only when it is explicitly provided.
        if (regRequest.getRole().equals(Role.ADMIN)) {
            user.setRole(Role.ADMIN);
        } else {
            // All other role values are treated as a normal USER.
            user.setRole(Role.USER);
        }

        // Persist the user and receive the saved entity, including generated fields.
        User savedUser = userRepository.save(user);

        // Build a response DTO so the encrypted password is not returned to the client.
        UserDTO savedUserDTO = new UserDTO();
        savedUserDTO.setEmail(savedUser.getEmail());
        savedUserDTO.setRole(savedUser.getRole());

        // Return the created status, a success message, and safe user information.
        return new ApiResponse<>(201, "User Saved Successfully", savedUserDTO);
    }

    /**
     * Authenticates a user and creates a server-side login session.
     *
     * The AuthenticationManager validates the email and password. After successful
     * authentication, the SecurityContext is saved in the HTTP session so subsequent
     * requests can recognize the logged-in user.
     */
    @Override
    public ApiResponse<?> login(RegLoginRequest loginRequest, HttpServletRequest request) {

        // Convert the submitted email and password into Spring Security credentials.

        // it calls the customUserDetailsService and customuserDetails class we created under hood to validate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Make the authenticated user available through Spring Security.

        // save the info to security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create or reuse the user's HTTP session for session-based authentication.

        // create a cookie session i.e. JSESSIONID for the users.
        // it is going to auto pass the session down when you accessing any endpoint via the set-cookie.
        HttpSession session = request.getSession(true);
        // Persist the security context in the session for future requests.
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        // Login succeeded; no sensitive authentication data is included in the response.

        // to return response back to the controller
        return new ApiResponse<>(200, "Login Successfully", null);
    }
}
