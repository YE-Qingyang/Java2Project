package com.example.java2project.service;

import com.example.java2project.domain.Commit;
import com.example.java2project.domain.Release;
import com.example.java2project.repository.CommitRepository;
import com.example.java2project.repository.ReleaseRepository;
import com.example.java2project.repository.RepositoryRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class ReleaseService {
  private final ReleaseRepository releaseRepository;
  private final CommitRepository commitRepository;
  private final RepositoryRepository repositoryRepository;

  @Autowired
  public ReleaseService(ReleaseRepository releaseRepository, CommitRepository commitRepository, RepositoryRepository repositoryRepository) {
    this.releaseRepository = releaseRepository;
    this.commitRepository = commitRepository;
    this.repositoryRepository = repositoryRepository;
  }

  public Integer countRelease(Long repoId) {
    return releaseRepository.countReleasesByRepository(repositoryRepository.getReferenceById(repoId));
  }

  public Map<String, Integer> countCommitBetweenRelease(Long repoId) {
    List<Release> releases = releaseRepository.findReleasesByRepository(repositoryRepository.getReferenceById(repoId));
    List<Commit> commits = commitRepository.findCommitsByRepository(repositoryRepository.getReferenceById(repoId));
    Map<String, Integer> map = new LinkedHashMap<>();
    // after the last one
    int cnt = 0;
    String last = releases.get(0).getPublish_date().replace("T", " ").replace("Z", "").trim();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date l = df.parse(last);
      for (Commit commit : commits) {
        String date = commit.getDate().replace("T", " ").replace("Z", "").trim();
        Date d = df.parse(date);
        if (d.getTime() - l.getTime() >= 0) {
          cnt++;
        }
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String range = "#" + releases.get(0).getRelease_id() + " ~ now";
    map.put(range, cnt);

    for (int i = 0; i < releases.size() - 1; i++) {
      String end = releases.get(i).getPublish_date().replace("T", " ").replace("Z", "").trim();
      String start = releases.get(i + 1).getPublish_date().replace("T", " ").replace("Z", "").trim();
      cnt = 0;
      try {
        Date d1 = df.parse(start);
        Date d2 = df.parse(end);
        for (Commit commit : commits) {
          String date = commit.getDate().replace("T", " ").replace("Z", "").trim();
          Date d = df.parse(date);
          if (d.getTime() - d1.getTime() >= 0 && d.getTime() - d2.getTime() < 0) {
            cnt++;
          }
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
      range = "#" + releases.get(i + 1).getRelease_id()
              + " ~ #" + releases.get(i).getRelease_id();
      map.put(range, cnt);
    }
    // before the first one
    cnt = 0;
    String first = releases.get(releases.size() - 1).getPublish_date().replace("T", " ").replace("Z", "").trim();
    try {
      Date f = df.parse(first);
      for (Commit commit : commits) {
        String date = commit.getDate().replace("T", " ").replace("Z", "").trim();
        Date d = df.parse(date);
        if (d.getTime() - f.getTime() < 0) {
          cnt++;
        }
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    range = "begin ~ " + "#" + releases.get(releases.size() - 1).getRelease_id();
    map.put(range, cnt);
    return map;
  }
}
