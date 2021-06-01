package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.CheckExistsDTO;
import com.hcl.capstoneserver.user.dto.ClientDTO;
import com.hcl.capstoneserver.user.dto.JwtWithTypeDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import com.hcl.capstoneserver.user.exceptions.EmailAlreadyExistsException;
import com.hcl.capstoneserver.user.exceptions.UserAlreadyExistsException;
import com.hcl.capstoneserver.user.repositories.AppUserRepository;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import com.hcl.capstoneserver.util.JWTUtil;
import com.hcl.capstoneserver.util.SequenceGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
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
    private final ClientRepository clientRepository;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper mapper;
    private final SequenceGenerator sequenceGenerator;

    public UserService(
            AppUserRepository appUserRepository,
            SupplierRepository supplierRepository,
            ClientRepository clientRepository,
            JWTUtil jwtUtil,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ModelMapper mapper,
            SequenceGenerator sequenceGenerator
    ) {
        this.appUserRepository = appUserRepository;
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
        this.sequenceGenerator = sequenceGenerator;
    }

    public JwtWithTypeDTO signIn(AppUser user) {
        try {

            UserDetails userDetails = loadUserByUsername(user.getUserId());
            if (!bCryptPasswordEncoder.matches(user.getPassword(), userDetails.getPassword()))
                throw new BadCredentialsException("Invalid username or password");

            String jwt = jwtUtil.generateToken(userDetails);

            return new JwtWithTypeDTO(
                    jwt,
                    userDetails.getAuthorities().toArray()[0].toString(),
                    user.getUserId()
            );
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public JwtWithTypeDTO refreshToken(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        String jwt = jwtUtil.generateToken(userDetails);

        return new JwtWithTypeDTO(
                jwt,
                userDetails.getAuthorities().toArray()[0].toString(),
                username
        );
    }

    // used by spring security don't change
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = appUserRepository.findById(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(
                user.get()
                    .getUserId(),
                user.get()
                    .getPassword(),
                Collections.singleton(
                        new SimpleGrantedAuthority(user.get().getUserType())
                )
        );
    }


    public SupplierDTO signUpSupplier(Supplier supplier) {
        try {
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
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException(supplier.getEmail());
        }
    }

    public ClientDTO signUpClient(Client client) {
        try {
            //check if the client is already exists or not
            if (clientRepository.existsById(client.getUserId())) {
                throw new UserAlreadyExistsException(client.getUserId());
            }

            return mapper.map(clientRepository.save(new Client(
                    client.getUserId(),
                    bCryptPasswordEncoder.encode(client.getPassword()),
                    client.getName(),
                    client.getAddress(),
                    client.getEmail(),
                    client.getPhone(),
                    client.getInterestRate(),
                    sequenceGenerator.getClientSequence(),
                    client.getAccountNumber()
            )), ClientDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException(client.getEmail());
        }
    }

    //check if supplier id exists
    public CheckExistsDTO checkSupplierId(String supplierId) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierId);
        boolean exists = supplierRepository.exists(Example.of(supplier));
        return new CheckExistsDTO(exists);
    }

    // get client id based on token
    public String getClientId(String userId) {
        Optional<Client> client = clientRepository.findById(userId);
        return client.map(Client::getClientId).orElse(null);
    }

    public Optional<Client> fetchClientById(String clientId) {
        return clientRepository.findById(clientId);
    }

    public Optional<Supplier> fetchSupplierById(String supplierId) {
        return supplierRepository.findById(supplierId);
    }
}

