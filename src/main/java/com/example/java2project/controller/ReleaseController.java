package com.example.java2project.controller;

import com.example.java2project.service.ReleaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/{repo}/release")
public class ReleaseController {

    @Resource
    ReleaseService releaseService;

    @RequestMapping("/count")
    public Integer countRelease(@PathVariable("repo") Long repoId) {
        return releaseService.countRelease(repoId);
    }

    @RequestMapping("/commit_count")
    public Map<String, Integer> commitCountBetweenRelease(@PathVariable("repo") Long repoId) {
        return releaseService.countCommitBetweenRelease(repoId);
    }
}
