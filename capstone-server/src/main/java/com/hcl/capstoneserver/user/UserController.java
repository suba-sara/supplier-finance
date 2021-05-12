package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.*;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController()
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;

    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/api/sign-in")
    public ResponseEntity<JwtWithTypeDTO> signIn(@RequestBody AppUserWithPasswordDTO dto) {
        return new ResponseEntity<>(
                userService.signIn(
                        dto.getUserId(),
                        dto.getPassword()
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/api/sign-up/supplier")
    public ResponseEntity<SupplierDTO> signUpSupplier(@Valid @RequestBody PersonWithPasswordDTO dto) {
        return new ResponseEntity<>(
                userService.signUpSupplier(mapper.map(dto, Supplier.class)),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/api/sign-up/client")
    public ResponseEntity<ClientDTO> signUpClient(@Valid @RequestBody PersonWithPasswordDTO dto) {
        return new ResponseEntity<>(
                userService.signUpClient(mapper.map(dto, Client.class)),
                HttpStatus.CREATED
        );
    }
}
