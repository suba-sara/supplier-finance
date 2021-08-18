package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.*;
import com.hcl.capstoneserver.user.entities.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Rest Controller for Users
 */
@CrossOrigin
@RestController()
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;

    /**
     * Constructor for UserController
     */
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * Method to signIn user
     *
     * @param dto the received data (username, password) to sign-in
     * @return send JWT-with-type-dto response entity
     */
    @PostMapping("/api/sign-in")
    public ResponseEntity<JwtWithTypeDTO> signIn(@Valid @RequestBody AppUserWithPasswordDTO dto) {
        return new ResponseEntity<>(
                userService.signIn(
                        mapper.map(dto, AppUser.class)
                ),
                HttpStatus.OK
        );
    }

    /**
     * Method to refresh JWT token
     *
     * @param principal the header comes with a request body
     * @return send JWT-with-type-dto response entity
     */
    @PostMapping("/api/refresh-token")
    public ResponseEntity<JwtWithTypeDTO> refreshToken(Principal principal) {
        /*
         * check principal(request has header or not) is null or not,
         * if it is null, throw an unauthorized error
         **/
        if (principal == null)
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "unauthorized");

        return new ResponseEntity<>(
                userService.refreshToken(principal.getName()),
                HttpStatus.OK
        );
    }

    /**
     * Method to sign-up supplier
     *
     * @param dto the data(type - PersonWithPasswordDTO) sent from front-end
     * @return send supplier-dto response entity
     */
    @PostMapping("/api/sign-up/supplier")
    public ResponseEntity<SupplierDTO> signUpSupplier(@Valid @RequestBody PersonWithPasswordDTO dto) {
        return new ResponseEntity<>(
                userService.signUpSupplier(dto),
                HttpStatus.CREATED
        );
    }

    /**
     * Method to sign-up client
     *
     * @param dto the data(type - PersonWithPasswordDTO) sent from front-end
     * @return send client-dto response entity
     */
    @PostMapping("/api/sign-up/client")
    public ResponseEntity<ClientDTO> signUpClient(@Valid @RequestBody PersonWithPasswordDTO dto) {
        return new ResponseEntity<>(
                userService.signUpClient(dto),
                HttpStatus.CREATED
        );
    }

    /**
     * Method to check supplier ID is exists or not
     *
     * @param supplierId the supplierId sent from front-end
     * @return if the supplier is existing return true, false return otherwise
     */
    @GetMapping("/api/users/checkSupplierId")
    public ResponseEntity<CheckExistsDTO> checkSupplierId(@RequestParam(name = "supplierId") String supplierId) {
        /*
         * send supplierId to checkSupplierId method, and it returns supplierId exist(true) or not(false)
         * */
        CheckExistsDTO existsDTO = userService.checkSupplierId(supplierId);
        return new ResponseEntity<>(
                existsDTO,
                existsDTO.isValid() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * Method to get the client id of the current user
     *
     * @param principal the header comes with a request body
     * @return send client-id-dto response entity
     */
    @GetMapping("/api/users/myClientId")
    public ResponseEntity<ClientIdDTO> getMyClientId(Principal principal) {
        /*
         * send the user_id to getClientId method and get clientId
         **/
        String clientId = userService.getClientId(principal.getName());

        // check client_Id is not null, then return below response entity
        if (clientId != null)
            return new ResponseEntity<>(
                    new ClientIdDTO(clientId),
                    HttpStatus.OK
            );

        // if client_Id is null, then return bellow response entity
        return new ResponseEntity<>(
                new ClientIdDTO(null),
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/api/users/profile")
    public ResponseEntity<ProfileDto> getProfile(Principal principal) {
        return new ResponseEntity<>(
                userService.getProfile(principal.getName()),
                HttpStatus.OK
        );
    }

    /**
     * Method to get OTP
     *
     * @param userId take userId
     * @return boolean
     */
    @PostMapping("/api/user/forgotPassword/getOTP")
    public ResponseEntity<CheckValidDTO> getOTP(@Valid @RequestBody String userId) {
        return new ResponseEntity<>(userService.getOTP(userId), HttpStatus.OK);
    }

    /**
     * Method to verified user account and update password
     *
     * @param dto take UserVerifiedDTO object
     * @return boolean
     */
    @PostMapping("/api/user/forgotPassword/verifyUser")
    public ResponseEntity<CheckValidDTO> verifyUser(@Valid @RequestBody UserVerifiedDTO dto) {
        return new ResponseEntity<>(userService.verifyUser(dto), HttpStatus.OK);
    }

    /**
     * Method to check userId is existed or not
     *
     * @param username
     * @return boolean
     */
    @PostMapping("/api/users/check-username")
    public ResponseEntity<CheckValidDTO> checkUserId(@Valid @RequestBody String username) {
        return new ResponseEntity<>(userService.checkUserId(username), HttpStatus.OK);
    }

    /**
     * Method to check email is existed or not
     *
     * @param email
     * @return boolean
     */
    @PostMapping("/api/users/check-email")
    public ResponseEntity<CheckValidDTO> checkEmail(@Valid @RequestBody String email) {
        return new ResponseEntity<>(userService.checkEmail(email), HttpStatus.OK);
    }
}
