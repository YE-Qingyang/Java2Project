package com.example.java2project.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Release {
    @Id
    @GeneratedValue
    private Long id;
    private Long release_id;
    private String publish_date;

    @ManyToOne
    @JoinColumn(name = "repo_id")
    private Repository repository;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelease_id() {
        return release_id;
    }

    public void setRelease_id(Long release_id) {
        this.release_id = release_id;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
