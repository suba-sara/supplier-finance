package com.hcl.capstoneserver.file;

import com.hcl.capstoneserver.file.entities.UploadedFile;
import com.hcl.capstoneserver.file.exceptions.UploadedFileInvalidTokenException;
import com.hcl.capstoneserver.file.exceptions.UploadedFileNotFoundException;
import com.hcl.capstoneserver.file.repositories.UploadedFileRepository;
import com.hcl.capstoneserver.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UploadedFileService {
    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private FileStorageService fileStorageService;


    public UploadedFile createInitialFile(String fileName) {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setUploaded(false);
        uploadedFile.setUri(fileName);
        uploadedFile.setToken(tokenGenerator.generateToken());

        uploadedFileRepository.save(uploadedFile);
        return uploadedFile;
    }

    private UploadedFile _fetchAndCheckExists(Integer id) {
        Optional<UploadedFile> uploadedFileObject = uploadedFileRepository.findById(id);

        // check if file exists
        if (!uploadedFileObject.isPresent()) {
            throw new UploadedFileNotFoundException(id);
        }

        return uploadedFileObject.get();
    }

    public String generateDownloadToken(Integer fileId) {
        UploadedFile file = _fetchAndCheckExists(fileId);
        String token = tokenGenerator.generateToken();

        file.setDownloadToken(token);
        uploadedFileRepository.save(file);
        return token;
    }

    public String uploadInvoiceFile(Integer id, String token, MultipartFile file) {
        UploadedFile uploadedFile = _fetchAndCheckExists(id);

        //check token
        if (uploadedFile.getToken() == null || !uploadedFile.getToken().equals(token)) {
            throw new UploadedFileInvalidTokenException();
        }

        String[] splittedName = file.getOriginalFilename().split("\\.");
        String fileUri = String.format(
                "%s.%s",
                uploadedFile.getUri(),
                splittedName[splittedName.length - 1]
        );

        // save file
        fileStorageService.save(
                file,
                fileUri
        );

        //update database
        uploadedFile.setToken(null);
        uploadedFile.setUploaded(true);
        uploadedFile.setUri(fileUri);
        uploadedFileRepository.save(uploadedFile);


        return uploadedFile.getUri();
    }

    public Resource getFile(Integer fileId, String token) {
        UploadedFile file = _fetchAndCheckExists(fileId);

        if (file.getDownloadToken() == null || !file.getDownloadToken().equals(token)) {
            throw new UploadedFileInvalidTokenException();
        }

        return fileStorageService.load(file.getUri());
    }

    public void deleteFile(UploadedFile file) {
        uploadedFileRepository.delete(file);
        fileStorageService.delete(file.getUri());
    }
}
