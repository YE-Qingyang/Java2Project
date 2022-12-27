package com.example.java2project.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Developer {
  @Id
  @GeneratedValue
  private Long id;
  private String name; // type="User": "login"; type="anonymous": "name"
  private Long user_id; // type="User"
  private String type;
  private String email; // type="Anonymous"
  private String url; // type="User"
  private Long contributions;

  @ManyToOne
  @JoinColumn(name = "repo_id")
  private Repository repository;

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

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getContributions() {
    return contributions;
  }

  public void setContributions(Long contributions) {
    this.contributions = contributions;
  }

  public Repository getRepository() {
    return repository;
  }

  public void setRepository(Repository repository) {
    this.repository = repository;
  }
}
