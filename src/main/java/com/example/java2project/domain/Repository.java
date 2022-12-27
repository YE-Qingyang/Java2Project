package com.example.java2project.domain;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table
public class Repository {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String full_name;
  private Long repo_id;
  private String url;

  private String owner;
  private Long owner_id;
  private String owner_url;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFull_name() {
    return full_name;
  }

  public void setFull_name(String full_name) {
    this.full_name = full_name;
  }

  public Long getRepo_id() {
    return repo_id;
  }

  public void setRepo_id(Long repo_id) {
    this.repo_id = repo_id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Long getOwner_id() {
    return owner_id;
  }

  public void setOwner_id(Long owner_id) {
    this.owner_id = owner_id;
  }

  public String getOwner_url() {
    return owner_url;
  }

  public void setOwner_url(String owner_url) {
    this.owner_url = owner_url;
  }

}
