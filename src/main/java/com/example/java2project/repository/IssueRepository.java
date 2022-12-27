package com.example.java2project.repository;

import com.example.java2project.domain.Issue;
import com.example.java2project.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    Integer countIssuesByRepositoryAndState(Repository repository, String state);

    List<Issue> findIssuesByRepositoryAndState(Repository repository, String state);

    List<Issue> findIssueByRepository(Repository repository);

}
