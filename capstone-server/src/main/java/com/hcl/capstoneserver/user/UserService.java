package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.account.AccountService;
import com.hcl.capstoneserver.account.dto.AccountVerifiedDTO;
import com.hcl.capstoneserver.account.exception.OTPTimedOut;
import com.hcl.capstoneserver.mail.sender.EmailService;
import com.hcl.capstoneserver.user.dto.*;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Banker;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import com.hcl.capstoneserver.user.exceptions.AppUserNotFoundException;
import com.hcl.capstoneserver.user.exceptions.EmailAlreadyExistsException;
import com.hcl.capstoneserver.user.exceptions.UserAlreadyExistsException;
import com.hcl.capstoneserver.user.exceptions.UserDoesNotExistException;
import com.hcl.capstoneserver.user.repositories.AppUserRepository;
import com.hcl.capstoneserver.user.repositories.BankerRepository;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import com.hcl.capstoneserver.util.EmailHideParts;
import com.hcl.capstoneserver.util.JWTUtil;
import com.hcl.capstoneserver.util.SequenceGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private final BankerRepository bankerRepository;
    private final AccountService accountService;
    private final EmailService emailService;

    @Value("${otp.validity.time}")
    private Integer otpValidityTime;

    @Value("${test.otp.seed}")
    private Integer testOtpSeed;

    @Value("${spring.env}")
    private String env;

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
            SequenceGenerator sequenceGenerator,
            BankerRepository bankerRepository,
            AccountService accountService,
            EmailService emailService
    ) {
        this.appUserRepository = appUserRepository;
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
        this.sequenceGenerator = sequenceGenerator;
        this.bankerRepository = bankerRepository;
        this.accountService = accountService;
        this.emailService = emailService;
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

    public ProfileDto getProfile(String userId) {
        Optional<AppUser> user = appUserRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        ProfileDto profileDto = new ProfileDto();
        profileDto.setUsername(userId);
        profileDto.setUserType(user.get().getUserType());

        switch (user.get().getUserType()) {
            case CLIENT:
                Optional<Client> client = clientRepository.findById(userId);
                profileDto.setClientId(client.get().getClientId());
                break;
            case SUPPLIER:
                Optional<Supplier> supplier = supplierRepository.findById(userId);
                profileDto.setSupplierId(supplier.get().getSupplierId());
                break;
            case BANKER:
                Optional<Banker> banker = bankerRepository.findById(userId);
                profileDto.setEmployeeId(banker.get().getEmployeeId());
                break;
        }

        return profileDto;
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
     * @return if suppler is not exists, then return supplierDTO object, otherwise throws error
     */
    public SupplierDTO signUpSupplier(PersonWithPasswordDTO dto) {
        try {
            // check if user already exists or not, if user is exists throw UserAlreadyExistsException
            if (supplierRepository.existsById(dto.getUserId())) {
                throw new UserAlreadyExistsException(dto.getUserId());
            }

            // check OTP
            accountService.verifyAccount(new AccountVerifiedDTO(dto.getAccountNumber(), dto.getOtp()));

            return mapper.map(supplierRepository.save(new Supplier(
                    dto.getUserId(),
                    bCryptPasswordEncoder.encode(dto.getPassword()),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getEmail(),
                    dto.getPhone(),
                    sequenceGenerator.getSupplierSequence(),
                    dto.getAccountNumber()
            )), SupplierDTO.class);
        } catch (DataIntegrityViolationException e) {
            //            if supplier email is already exists then throw DataIntegrityViolationException and catch
            //            form here and throw below error
            throw new EmailAlreadyExistsException(dto.getEmail());
        }
    }

    /**
     * Method to register new client
     *
     * @return if client is not exists, then return clientDTO object, otherwise throws error
     */
    public ClientDTO signUpClient(PersonWithPasswordDTO dto) {
        try {
            //check if the client is already exists or not, if user  exists throw UserAlreadyExistsException
            if (clientRepository.existsById(dto.getUserId())) {
                throw new UserAlreadyExistsException(dto.getUserId());
            }

            // check OTP
            accountService.verifyAccount(new AccountVerifiedDTO(dto.getAccountNumber(), dto.getOtp()));

            return mapper.map(clientRepository.save(new Client(
                    dto.getUserId(),
                    bCryptPasswordEncoder.encode(dto.getPassword()),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getEmail(),
                    dto.getPhone(),
                    sequenceGenerator.getClientSequence(),
                    dto.getAccountNumber()
            )), ClientDTO.class);
        } catch (DataIntegrityViolationException e) {
            //            if client email is already exists then throw DataIntegrityViolationException and catch form
            //            here and throw below error
            throw new EmailAlreadyExistsException(dto.getEmail());
        }
    }

    /**
     * Method to create Banker
     *
     * @param banker bank data to register new banker
     * @return if bank is not exists, then return bankDTO object, otherwise throws error
     */
    public BankerDTO createBanker(Banker banker) {
        //check if the banker is already exists or not, if user is exists throw UserAlreadyExistsException
        if (bankerRepository.existsById(banker.getUserId())) {
            throw new UserAlreadyExistsException(banker.getUserId());
        }

        return mapper.map(bankerRepository.save(new Banker(
                banker.getUserId(),
                bCryptPasswordEncoder.encode(banker.getPassword()),
                banker.getEmployeeId()
        )), BankerDTO.class);
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
            throw new UserDoesNotExistException(UserType.CLIENT, "User Id");
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
            throw new UserDoesNotExistException(UserType.SUPPLIER, "User Id");
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
            throw new UserDoesNotExistException(UserType.SUPPLIER, "Client Id");
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
            throw new UserDoesNotExistException(UserType.SUPPLIER, "Supplier Id");
        }
        return optionalSupplier.get();
    }

    /**
     * Method to send OTP code for forgot password
     *
     * @param userId userId
     * @return if OTP code is correct, then return true. and other hand return following exception
     */
    public CheckValidDTO getOTP(String userId) {
        // retrieve user from db
        Optional<AppUser> user = appUserRepository.findById(userId);

        // check user object is null, then throw following exception
        if (!user.isPresent()) {
            throw new AppUserNotFoundException();
        }

        Random r;
        if (env.equals("test")) {
            r = new Random(testOtpSeed);
        } else {
            r = new Random();
        }

        String otp = String.valueOf(10000000 + r.nextInt(99999999));

        String email = null;
        // check the User account Type
        switch (user.get().getUserType()) {
            case CLIENT:
                email = fetchClientDataByUserId(userId).getEmail();
                // send OTP Code
                emailService.sendForgotPasswordOTP(email, otp);
                // Save OTP code and OTP Code expired date
                appUserRepository.save(_addOTPAndExpireDate(user.get(), otp));
                break;
            case SUPPLIER:
                email = fetchSupplierDataByUserId(userId).getEmail();
                // send OTP Code
                emailService.sendForgotPasswordOTP(email, otp);
                // Save OTP code and OTP Code expired date
                appUserRepository.save(_addOTPAndExpireDate(user.get(), otp));
                break;
        }

        return new CheckValidDTO(true, new EmailHideParts(email).hide());
    }

    /**
     * Method to verify OTP and update password
     *
     * @param dto UserVerifiedDto object -> for more information move on it
     * @return if OTP code is correct, then return true. and other hand return following exception
     */
    public CheckValidDTO verifyUser(UserVerifiedDTO dto) {
        // retrieve user from db
        Optional<AppUser> user = appUserRepository.findById(dto.getUserId());

        // check user object is null or not
        if (user.isPresent()) {
            // check the OTP time is expired or not
            if (new Date().before(user.get().getOtpExpiredDate())) {
                // check OTP is equal or not
                if (user.get().getOtp().equals(dto.getOTP())) {
                    /*
                     * set new password
                     */
                    user.get().setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
                    appUserRepository.save(user.get());
                    return new CheckValidDTO(true, null);
                }
            } else {
                // if OTP is expired then throw this exception
                throw new OTPTimedOut();
            }
        }

        // if user object is null then throw this exception
        throw new AppUserNotFoundException();
    }

    /**
     * Method to set OTP and OTP expired date
     *
     * @param user AppUser object
     * @param OTP  One-time-password
     * @return AppUser object
     */
    private AppUser _addOTPAndExpireDate(AppUser user, String OTP) {
        // set OTP
        user.setOtp(OTP);

        /*
         * set OTP expired date
         *
         * logic -> take the system current time and add OTP validity time to it.
         * this OTP validity time is in resource property file and can change it
         */
        user.setOtpExpiredDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(otpValidityTime)));
        return user;
    }

    public CheckValidDTO checkUserId(String userId) {
        return new CheckValidDTO(!appUserRepository.existsById(userId), null);
    }

    public CheckValidDTO checkEmail(String email) {
        return new CheckValidDTO(!clientRepository.existsClientByEmail(email) && !supplierRepository.existsSupplierByEmail(
                email), null);
    }
}

