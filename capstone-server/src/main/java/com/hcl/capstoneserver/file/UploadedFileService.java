package com.hcl.capstoneserver.file;

import com.hcl.capstoneserver.file.repositories.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadedFileService {
    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    public boolean uploadFile() {
        return true;
    }
}
