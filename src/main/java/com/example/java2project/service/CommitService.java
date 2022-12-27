package com.example.java2project.service;

import com.example.java2project.domain.Commit;
import com.example.java2project.repository.CommitRepository;
import com.example.java2project.repository.RepositoryRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommitService {
  private final CommitRepository commitRepository;
  private final RepositoryRepository repositoryRepository;

  @Autowired
  public CommitService(CommitRepository commitRepository,
                       RepositoryRepository repositoryRepository) {
    this.commitRepository = commitRepository;
    this.repositoryRepository = repositoryRepository;
  }

  public Integer commitCount(Long repoId) {
    return commitRepository.countCommitsByRepository(repositoryRepository.getReferenceById(repoId));
  }

  public Map<String, Integer> commitDistribution(Long repoId) {
    List<Commit> commitList = commitRepository.findCommitsByRepository(repositoryRepository.getReferenceById(repoId));
    int midnight = 0;
    int morning = 0;
    int afternoon = 0;
    int evening = 0;
    int weekday = 0;
    int weekend = 0;
    for (Commit commit : commitList) {
      // 00:00 ~ 06:00 : midnight
      // 06:00 ~ 12:00 : morning
      // 12:00 ~ 18:00 : afternoon
      // 18:00 ~ 00:00 : evening
      String time = commit.getDate().replace("T", " ").replace("Z", "").trim();
      String[] t = time.split(" ")[1].split(":");
      int h = Integer.parseInt(t[0]);
      if (h >= 0 && h < 6) {
        midnight++;
      }
      if (h >= 6 && h < 12) {
        morning++;
      }
      if (h >= 12 && h < 18) {
        afternoon++;
      }
      if (h >= 18 && h < 24) {
        evening++;
      }
      // weekday or weekend
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
        Date date = df.parse(time);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        String week = sdf.format(date);
        if (week.equals("Sunday") || week.equals("Saturday")) {
          weekend++;
        } else {
          weekday++;
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("midnight", midnight);
    map.put("morning", morning);
    map.put("afternoon", afternoon);
    map.put("evening", evening);
    map.put("weekday", weekday);
    map.put("weekend", weekend);
    return map;
  }
}
