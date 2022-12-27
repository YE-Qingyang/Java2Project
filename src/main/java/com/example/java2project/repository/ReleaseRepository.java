package com.example.java2project.repository;

import com.example.java2project.domain.Release;
import com.example.java2project.domain.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ReleaseRepository extends JpaRepository<Release, Long> {
  Integer countReleasesByRepository(Repository repository);

  List<Release> findReleasesByRepository(Repository repository);
}
