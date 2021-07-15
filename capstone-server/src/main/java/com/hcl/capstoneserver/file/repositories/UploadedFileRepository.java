package com.hcl.capstoneserver.file.repositories;

import com.hcl.capstoneserver.file.entities.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Integer> {
}
