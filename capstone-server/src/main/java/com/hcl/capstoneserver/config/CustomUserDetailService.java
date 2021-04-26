package com.hcl.capstoneserver.config;

import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(s);

        if (user == null)
            throw new UsernameNotFoundException("User not found");


        return new CustomUserDetails(user);

    }
}
