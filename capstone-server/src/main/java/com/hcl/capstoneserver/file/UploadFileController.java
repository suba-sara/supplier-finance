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

    @PostMapping("/api/files/invoice/upload/{id}")
    public ResponseEntity<String> uploadFile(
            @PathVariable Integer id,
            @RequestParam MultipartFile file,
            @RequestParam String token
    ) {
        return new ResponseEntity<>(uploadedFileService.uploadInvoiceFile(id, token, file), HttpStatus.CREATED);
    }

}
