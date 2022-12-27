package com.example.java2project.controller;

import com.example.java2project.service.IssueService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/{repo}/issue")
public class IssueController {

    @Resource
    IssueService issueService;

    @RequestMapping("/count/{state}")
    public Integer openCount(@PathVariable("repo") Long repoId,
                             @PathVariable("state") String state){
        return issueService.getIssueCount(repoId, state);
    }

    @RequestMapping("/resolution")
    public Map<String, String> resolutionResult(@PathVariable("repo") Long repoId) {
        return issueService.resolution(repoId);
    }

    @RequestMapping("/resolution_time")
    public List<Long> resolutionTime(@PathVariable("repo") Long repoId) {
        return issueService.resolutionTime(repoId);
    }

    @RequestMapping("/word_count")
    public Map<String, Long> wordCount(@PathVariable("repo") Long repoId) {
        return issueService.wordCount(repoId);
    }
}
