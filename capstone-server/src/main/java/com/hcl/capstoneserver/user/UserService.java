package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.JwtWithTypeDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Supplier;
import com.hcl.capstoneserver.user.exceptions.UserAlreadyExistsException;
import com.hcl.capstoneserver.user.repositories.AppUserRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import com.hcl.capstoneserver.util.JWTUtil;
import com.hcl.capstoneserver.util.SequenceGenerator;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper mapper;
    private final SequenceGenerator sequenceGenerator;

    public UserService(AppUserRepository appUserRepository, SupplierRepository supplierRepository,
                       JWTUtil jwtUtil,
                       BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper mapper,
                       SequenceGenerator sequenceGenerator) {
        this.appUserRepository = appUserRepository;
        this.supplierRepository = supplierRepository;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
        this.sequenceGenerator = sequenceGenerator;
    }

    public JwtWithTypeDTO signIn(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Invalid username or password");

        String jwt = jwtUtil.generateToken(userDetails);

        return new JwtWithTypeDTO(jwt, userDetails.getAuthorities().toArray()[0].toString());
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


    public SupplierDTO signUpSupplier(Supplier supplier) {
        //check if user already exists
        if (supplierRepository.existsById(supplier.getUserId())) {
            throw new UserAlreadyExistsException(supplier.getUserId());
        }


        return mapper.map(supplierRepository.save(new Supplier(
                supplier.getUserId(),
                bCryptPasswordEncoder.encode(supplier.getPassword()),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getInterestRate(),
                sequenceGenerator.getSupplierSequence()
                )), SupplierDTO.class);
    }
}

