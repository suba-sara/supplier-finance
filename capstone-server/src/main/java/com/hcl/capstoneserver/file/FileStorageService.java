package com.hcl.capstoneserver.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void save(MultipartFile file, String name);

    Resource load(String filename);

    void delete(String filename);
}
