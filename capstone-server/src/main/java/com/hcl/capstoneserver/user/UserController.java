package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.SignInRequestDTO;
import com.hcl.capstoneserver.user.dto.SignInResponseDTO;
import com.hcl.capstoneserver.user.dto.SignUpResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController()
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
