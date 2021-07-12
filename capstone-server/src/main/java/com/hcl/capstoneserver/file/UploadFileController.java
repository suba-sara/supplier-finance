package com.hcl.capstoneserver.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class UploadFileController {
    @Autowired
    private UploadedFileService uploadedFileService;

    @PostMapping("/api/files/upload/{id}")
    public ResponseEntity<String> uploadFile(
            @PathVariable Integer id,
            @RequestParam("token") String token,
            @RequestParam("file") MultipartFile file
    ) {
        return new ResponseEntity<>(uploadedFileService.uploadFile(id, token, file), HttpStatus.CREATED);
    }
}
