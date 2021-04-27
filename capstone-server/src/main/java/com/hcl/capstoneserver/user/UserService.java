package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.SignInResponseDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.repositories.AppUserRepository;
import com.hcl.capstoneserver.util.JWTUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final JWTUtil jwtUtil;

    public UserService(AppUserRepository appUserRepository, JWTUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.jwtUtil = jwtUtil;
    }

    public SignInResponseDTO signIn(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if (!new BCryptPasswordEncoder().matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Invalid username or password");

        String jwt = jwtUtil.generateToken(userDetails);

        return new SignInResponseDTO(jwt);
    }

    // used by spring security don't change
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = appUserRepository.findById(username);

        if (!user.isPresent())
            throw new UsernameNotFoundException("User not found");

        return new User(
                user.get().getUserId(),
                user.get().getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.get().getUserRole().toString()))
        );
    }
}
