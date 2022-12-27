package com.example.java2project.service;

import com.example.java2project.domain.Issue;
import com.example.java2project.repository.IssueRepository;
import com.example.java2project.repository.RepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final RepositoryRepository repositoryRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository, RepositoryRepository repositoryRepository) {
        this.issueRepository = issueRepository;
        this.repositoryRepository = repositoryRepository;
    }

    public Integer getIssueCount(Long repoId, String state) {
        return issueRepository.countIssuesByRepositoryAndState(repositoryRepository.getReferenceById(repoId), state);
    }

    public Map<String, String> resolution(Long repoId) {
        List<Long> resolution = resolutionTime(repoId);
        Map<String, String> map = new LinkedHashMap<>();
        // average
        double avg = resolution.stream().collect(Collectors.averagingLong(x -> x));
        long time = (long) avg;
        long nd = 24 * 60* 60;
        long nh = 60 * 60;
        long nm = 60;
        long day = time / nd;
        long hour = time % nd / nh;
        long min = time % nd % nh / nm;
        long sec = time % nd % nh % nm;
        map.put("average", day + "d " + hour + "h " + min + "m " + sec + "s");
        // range
        double range = (double) (Collections.max(resolution) - Collections.min(resolution));
        time = (long) range;
        day = time / nd;
        hour = time % nd / nh;
        min = time % nd % nh / nm;
        sec = time % nd % nh % nm;
        map.put("range", day + "d " + hour + "h " + min + "m " + sec + "s");
        // variance
        double var = 0;
        for (Long r : resolution) {
            var += (r - avg) * (r - avg);
        }
        var = var / resolution.size();
        map.put("variance", String.format("%.2f", var));
        // std
        double std = Math.sqrt(var);
        map.put("standard deviation", String.format("%.2f", std));
        return map;
    }

    public List<Long> resolutionTime(Long repoId) {
        List<Long> resolution = new ArrayList<>();
        List<Issue> issues = issueRepository.findIssuesByRepositoryAndState(repositoryRepository.getReferenceById(repoId), "closed");
        for (Issue i : issues) {
            String start = i.getCreate_date().replace("T", " ").replace("Z", "").trim();
            String end = i.getClose_date().replace("T", " ").replace("Z", "").trim();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date d1 = df.parse(end);
                Date d2 = df.parse(start);
                long diff = d1.getTime() - d2.getTime();
                resolution.add(diff / 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return resolution;
    }

    public Map<String, Long> wordCount(Long repoId) {
        List<Issue> issues = issueRepository.findIssueByRepository(repositoryRepository.getReferenceById(repoId));
        Map<String, Long> map = new HashMap<>();
        for (Issue issue : issues) {
            String body = issue.getBody();
            String title = issue.getTitle();
            String newBody = "";
            String newTitle = "";
            if (body != null) {
                newBody = body.replaceAll("[^A-Za-z ]", " ");
            }
            if (title != null) {
                newTitle = title.replaceAll("[^A-Za-z ]", " ");
            }
            StringTokenizer tokenizer = new StringTokenizer(newBody + " " + newTitle);
            if (tokenizer.hasMoreElements()) {
                String word = tokenizer.nextToken().toLowerCase();
                if (!map.containsKey(word)) {
                    map.put(word, 1L);
                } else {
                    long k = map.get(word) + 1;
                    map.put(word, k);
                }
            }
        }
        // choose top 50
        List<Map.Entry<String, Long>> list = new ArrayList<>(map.entrySet());
        list.sort(new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return (int) (o2.getValue() - o1.getValue());
            }
        });
        Map<String, Long> sortMap = new LinkedHashMap<>();
        for (int i = 0; i < 50; i++) {
            sortMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return sortMap;
    }

}
