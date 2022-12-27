package com.example.java2project.repository;

import com.example.java2project.domain.Developer;
import com.example.java2project.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Integer countDevelopersByRepository(Repository repository);

    List<Developer> findTop5DevelopersByRepositoryOrderByContributionsDesc(Repository repository);
}
