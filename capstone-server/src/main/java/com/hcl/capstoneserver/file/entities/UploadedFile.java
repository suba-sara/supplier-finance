package com.hcl.capstoneserver.file.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UploadedFile {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String uri;

    public UploadedFile() {
    }

    public UploadedFile(String uri) {
        this.uri = uri;
    }

    public UploadedFile(Integer id, String uri) {
        this.id = id;
        this.uri = uri;
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
}
