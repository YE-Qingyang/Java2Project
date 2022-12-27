package com.example.java2project.repository;

import com.example.java2project.domain.Commit;
import com.example.java2project.domain.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommitRepository extends JpaRepository<Commit, Long> {
  Integer countCommitsByRepository(Repository repository);

  List<Commit> findCommitsByRepository(Repository repository);
}
