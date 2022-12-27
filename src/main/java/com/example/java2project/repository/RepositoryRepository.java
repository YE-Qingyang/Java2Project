package com.example.java2project.repository;

import com.example.java2project.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
}
