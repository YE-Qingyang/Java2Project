package com.example.java2project.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Issue {
  @Id
  @GeneratedValue
  private Long id;
  private Long issue_id;
  @Column(length = 1024)
  private String title;
  private String state;
  @Column(length = 20000)
  private String body;
  private String create_date;
  private String close_date; // state="closed"

  @ManyToOne
  @JoinColumn(name = "repo_id")
  private Repository repository;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIssue_id() {
    return issue_id;
  }

  public void setIssue_id(Long issue_id) {
    this.issue_id = issue_id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getCreate_date() {
    return create_date;
  }

  public void setCreate_date(String create_date) {
    this.create_date = create_date;
  }

  public String getClose_date() {
    return close_date;
  }

  public void setClose_date(String close_date) {
    this.close_date = close_date;
  }

  public Repository getRepository() {
    return repository;
  }

  public void setRepository(Repository repository) {
    this.repository = repository;
  }
}
