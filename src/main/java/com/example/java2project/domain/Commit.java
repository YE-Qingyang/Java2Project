package com.example.java2project.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Commit {
  @Id
  @GeneratedValue
  private Long id;
  private String sha;
  private String date;

  @ManyToOne
  @JoinColumn(name = "repo_id")
  private Repository repository;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSha() {
    return sha;
  }

  public void setSha(String sha) {
    this.sha = sha;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Repository getRepository() {
    return repository;
  }

  public void setRepository(Repository repository) {
    this.repository = repository;
  }
}
