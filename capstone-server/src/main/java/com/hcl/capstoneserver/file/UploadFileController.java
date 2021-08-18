package com.hcl.capstoneserver.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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


    @PostMapping("/api/files/invoice/upload/{id}")
    public ResponseEntity<String> uploadFile(
            @PathVariable Integer id,
            @RequestParam MultipartFile file,
            @RequestParam String token
    ) {
        return new ResponseEntity<>(uploadedFileService.uploadInvoiceFile(id, token, file), HttpStatus.CREATED);
    }

    // get invoice file
    @GetMapping("/api/getFile/{fileId}")
    public ResponseEntity<Resource> getInvoiceFile(
            @Valid @PathVariable Integer fileId,
            @Valid @RequestParam String token
    ) {
        Resource file = uploadedFileService.getFile(fileId, token);
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\""
        ).body(file);
    }
}
