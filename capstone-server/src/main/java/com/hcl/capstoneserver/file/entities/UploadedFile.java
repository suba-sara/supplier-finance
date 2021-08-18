package com.hcl.capstoneserver.file.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UploadedFile {
    @Id
    @GeneratedValue
    private Integer id;

    private String uri;
    private boolean isUploaded = false;
    private String token;
    private String downloadToken;


    public UploadedFile() {
    }


    public UploadedFile(Integer id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public UploadedFile(Integer id, String uri, boolean isUploaded, String token, String downloadToken) {
        this.id = id;
        this.uri = uri;
        this.isUploaded = isUploaded;
        this.token = token;
        this.downloadToken = downloadToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDownloadToken() {
        return downloadToken;
    }

    public void setDownloadToken(String downloadToken) {
        this.downloadToken = downloadToken;
    }
}
