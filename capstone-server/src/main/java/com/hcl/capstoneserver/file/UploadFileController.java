package com.hcl.capstoneserver.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class UploadFileController {
    @Autowired
    private UploadedFileService uploadedFileService;

    // get invoice file
    @GetMapping("/api/file/{fileId}")
    public Resource getInvoiceFile(
            @Valid @PathVariable Integer fileId,
            @Valid @RequestParam String token
    ) {
        return uploadedFileService.getFile(fileId, token);
    }

    @PostMapping("/api/files/invoice/upload/{id}")
    public ResponseEntity<String> uploadFile(
            @PathVariable Integer id,
            @RequestParam MultipartFile file,
            @RequestParam String token
    ) {
        return new ResponseEntity<>(uploadedFileService.uploadInvoiceFile(id, token, file), HttpStatus.CREATED);
    }

}
