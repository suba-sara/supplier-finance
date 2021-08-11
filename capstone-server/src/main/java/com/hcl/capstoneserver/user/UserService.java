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
import com.hcl.capstoneserver.user.exceptions.UserDoesNotExistException;
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

/**
 * Service for Users
 */
@Service
public class UserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final SupplierRepository supplierRepository;
    private final ClientRepository clientRepository;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper mapper;
    private final SequenceGenerator sequenceGenerator;

    /**
     * Constructor for UserService
     */
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


    /**
     * Method to signIn user
     *
     * @param user the user data(userId, password) to sign-in
     * @return if user is sign-in success, then return JwtWithTypeDTO object, otherwise throws BadCredentials error
     */
    public JwtWithTypeDTO signIn(AppUser user) {
        try {

            UserDetails userDetails = loadUserByUsername(user.getUserId());

            /*
             * check db user password and sign-in user password
             * bCryptPasswordEncoder uses to encode and check both password are same or not
             * if passwords are mismatch then throw BadCredentialsException
             **/
            if (!bCryptPasswordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            }
            /*
             * if both passwords are mathe then generate JWT token and return JwtWithTypeDTO object
             **/
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

    /**
     * Method to refresh JWT token
     *
     * @param username the username of the current sign-in user
     * @return new JwtWithTypeDTO object/ return new JWT token
     */
    public JwtWithTypeDTO refreshToken(String username) {
        // load user by username
        UserDetails userDetails = loadUserByUsername(username);

        // generate new jwt token
        String jwt = jwtUtil.generateToken(userDetails);

        return new JwtWithTypeDTO(
                jwt,
                userDetails.getAuthorities().toArray()[0].toString(),
                username
        );
    }

    /**
     * Method to get user by username
     *
     * @param username username of the user need to search, get
     * @return UserDetails object
     * @throws UsernameNotFoundException if user is not present throw this error
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
         * AppUser table primary key is username -> remember
         *
         * find User using the AppUser repository and assign it to user local variable
         * */
        Optional<AppUser> user = appUserRepository.findById(username);

        // check user is null or not, if user is null throw error
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(
                user.get()
                        .getUserId(),
                user.get()
                        .getPassword(),
                Collections.singleton(
                        new SimpleGrantedAuthority(user.get().getUserType().toString())
                )
        );
    }


    /**
     * Method to register new supplier
     *
     * @param supplier supplier data to register new supplier
     * @return if suppler is not exists, then return supplierDTO object, otherwise throws error
     */
    public SupplierDTO signUpSupplier(Supplier supplier) {
        try {
            // check if user already exists or not, if user is exists throw UserAlreadyExistsException
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
//            if supplier email is already exists then throw DataIntegrityViolationException and catch form here and throw below error
            throw new EmailAlreadyExistsException(supplier.getEmail());
        }
    }

    /**
     * Method to register new client
     *
     * @param client client data to register new supplier
     * @return if client is not exists, then return clientDTO object, otherwise throws error
     */
    public ClientDTO signUpClient(Client client) {
        try {
            //check if the client is already exists or not, if user is exists throw UserAlreadyExistsException
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
//            if client email is already exists then throw DataIntegrityViolationException and catch form here and throw below error
            throw new EmailAlreadyExistsException(client.getEmail());
        }
    }

    /**
     * Method to check supplierID
     *
     * @param supplierId id of the supplier
     * @return CheckExistsDTO object return, if exists return true, otherwise return false
     */
    public CheckExistsDTO checkSupplierId(String supplierId) {
        // make new local supplier object
        Supplier supplier = new Supplier();
        // set supplierId to create supplier local object
        supplier.setSupplierId(supplierId);
        // check supplier is exists or not
        boolean exists = supplierRepository.exists(Example.of(supplier));
        return new CheckExistsDTO(exists);
    }

    /**
     * Method to get client id based on username
     *
     * @param userId username of the current sign-in user
     * @return return sign-in client id
     */
    public String getClientId(String userId) {
        /*
         * Client table primary key is username -> remember
         *
         * find User using the client repository and assign it to client local variable
         * */
        Optional<Client> client = clientRepository.findById(userId);
        return client.map(Client::getClientId).orElse(null);
    }

    /**
     * Method to get supplier id based on username
     *
     * @param userId username of the current sign-in user
     * @return return sign-in supplier id
     */
    public String getSupplierId(String userId) {
        /*
         * Supplier table primary key is username -> remember
         *
         * find User using the supplier repository and assign it to supplier local variable
         * */
        Optional<Supplier> supplier = supplierRepository.findById(userId);
        return supplier.map(Supplier::getSupplierId).orElse(null);
    }

    /**
     * Method to fetch Client data using userId
     *
     * @param userId username of the current sign-in user
     * @return if user is present, then return client object, otherwise throws error
     */
    public Client fetchClientDataByUserId(String userId) {
        /*
         * Client table primary key is username -> remember
         *
         * find User using the client repository and assign it to client local variable
         * */
        Optional<Client> client = clientRepository.findById(userId);

        // check client variable is null or not, if its null throws UserDoesNotExistException
        if (!client.isPresent()) {
            throw new UserDoesNotExistException(UserType.CLIENT, "userId");
        }
        return client.get();
    }

    /**
     * Method to fetch Supplier data using userId
     *
     * @param userId username of the current sign-in user
     * @return if user is present, then return client object, otherwise throws error
     */
    public Supplier fetchSupplierDataByUserId(String userId) {
        /*
         * Supplier table primary key is username -> remember
         *
         * find User using the supplier repository and assign it to supplier local variable
         * */
        Optional<Supplier> supplier = supplierRepository.findById(userId);

        // check supplier variable is null or not, if its null throws UserDoesNotExistException
        if (!supplier.isPresent()) {
            throw new UserDoesNotExistException(UserType.SUPPLIER, "userId");
        }
        return supplier.get();
    }

    /**
     * Method to fetch Client data using client id
     *
     * @param clientId clientId to fetch Client
     * @return if user is present, then return client object, otherwise throws error
     */
    public Client fetchClientDataByClientId(String clientId) {
        // make new local client object
        Client client = new Client();
        // set clientId to create client local object
        client.setClientId(clientId);
        /*
         * find User using the client repository and assign it to optionalClient local variable
         * */
        Optional<Client> optionalClient = clientRepository.findOne(Example.of(client));

        // check client variable is null or not, if its null throws UserDoesNotExistException
        if (!optionalClient.isPresent()) {
            throw new UserDoesNotExistException(UserType.SUPPLIER, "clientId");
        }
        return optionalClient.get();
    }

    /**
     * Method to fetch Supplier data using supplier id
     *
     * @param supplierId supplierId to fetch Supplier
     * @return if user is present, then return client object, otherwise throws error
     */
    public Supplier fetchSupplierDataBySupplierId(String supplierId) {
        // make new local supplier object
        Supplier supplier = new Supplier();
        // set supplierId to create supplier local object
        supplier.setSupplierId(supplierId);
        /*
         * find User using the supplier repository and assign it to optionalSupplier local variable
         * */
        Optional<Supplier> optionalSupplier = supplierRepository.findOne(Example.of(supplier));
        // check supplier variable is null or not, if its null throws UserDoesNotExistException
        if (!optionalSupplier.isPresent()) {
            throw new UserDoesNotExistException(UserType.SUPPLIER, "supplierId");
        }
        return optionalSupplier.get();
    }
}

