package com.example.java2project.service;

import com.example.java2project.domain.Developer;
import com.example.java2project.repository.DeveloperRepository;
import com.example.java2project.repository.RepositoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DeveloperService {
  private final DeveloperRepository developerRepository;
  private final RepositoryRepository repositoryRepository;

  @Autowired
  public DeveloperService(DeveloperRepository developerRepository, RepositoryRepository repositoryRepository) {
    this.developerRepository = developerRepository;
    this.repositoryRepository = repositoryRepository;
  }

  public Integer countDeveloperByRepo(Long repoId) {
    return developerRepository.countDevelopersByRepository(repositoryRepository.getReferenceById(repoId));
  }

  public List<Developer> top5Developer(Long repoId) {
    return developerRepository.findTop5DevelopersByRepositoryOrderByContributionsDesc(repositoryRepository.getReferenceById(repoId));
  }
}
