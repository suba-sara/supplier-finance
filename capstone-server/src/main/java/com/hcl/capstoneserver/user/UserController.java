package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.SignInRequestDTO;
import com.hcl.capstoneserver.user.dto.SignInResponseDTO;
import com.hcl.capstoneserver.user.dto.SignUpSupplierRequestDTO;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        return new ResponseEntity<>(
                userService.signIn(
                        signInRequestDTO.getUsername(),
                        signInRequestDTO.getPassword()
                ),
                HttpStatus.OK
        );
    }

//    testing only -> implement proper methods
//    @PostMapping("/api/sign-up")
//    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignInRequestDTO dto) {
//        return new ResponseEntity<>(userService.signUp(dto), HttpStatus.CREATED);
//    }

    @PostMapping("/api/sign-up/supplier")
    public ResponseEntity<Supplier> signUpSupplier(@RequestBody SignUpSupplierRequestDTO dto) {
        return new ResponseEntity<>(userService.signUpSupplier(dto), HttpStatus.CREATED);
    }
}
