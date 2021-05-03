package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.SignInResponseDTO;
import com.hcl.capstoneserver.user.dto.SignUpResponseDTO;
import com.hcl.capstoneserver.user.dto.SignUpSupplierRequestDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Supplier;
import com.hcl.capstoneserver.user.exceptions.UserAlreadyExistsException;
import com.hcl.capstoneserver.user.repositories.AppUserRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import com.hcl.capstoneserver.util.JWTUtil;
import org.springframework.context.annotation.Lazy;
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
    private final SupplierRepository supplierRepository;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(AppUserRepository appUserRepository, SupplierRepository supplierRepository,
                       JWTUtil jwtUtil, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.supplierRepository = supplierRepository;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public SignInResponseDTO signIn(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Invalid username or password");

        String jwt = jwtUtil.generateToken(userDetails);

        return new SignInResponseDTO(jwt, userDetails.getAuthorities().toArray()[0].toString());
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
                Collections.singleton(new SimpleGrantedAuthority((user.get().getUserType())))
        );
    }

//    testing only -> implement proper methods
//    public SignUpResponseDTO signUp(SignInRequestDTO dto) {
//        if (appUserRepository.existsById(dto.getUsername())) {
//            throw new UserAlreadyExistsException(dto.getUsername());
//        }
//
//        AppUser createdUser = appUserRepository.save(new AppUser(
//                dto.getUsername(),
//                bCryptPasswordEncoder.encode(dto.getPassword())
//        ));
//
//        return new SignUpResponseDTO(createdUser.getUserId(), createdUser.getUserType());
//    }

    public Supplier signUpSupplier(SignUpSupplierRequestDTO dto) {
        //check if user already exists
        if (supplierRepository.existsById(dto.getUsername())) {
            throw new UserAlreadyExistsException(dto.getUsername());
        }

        return supplierRepository.save(new Supplier(
                dto.getUsername(),
                bCryptPasswordEncoder.encode(dto.getPassword()),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getInterestRate()
        ));
    }
}

