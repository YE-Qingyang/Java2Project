package com.example.java2project.controller;

import com.example.java2project.service.CommitService;
import com.example.java2project.service.ReleaseService;
import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/{repo}/commit")
public class CommitController {

  @Resource
  CommitService commitService;

  @RequestMapping("/count")
  public Integer countCommit(@PathVariable("repo") Long repoId) {
    return commitService.commitCount(repoId);
  }

  @RequestMapping("/distribution")
  public Map<String, Integer> commitDistribution(@PathVariable("repo") Long repoId) {
    return commitService.commitDistribution(repoId);
  }
}
