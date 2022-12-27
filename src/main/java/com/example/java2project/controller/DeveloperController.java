package com.example.java2project.controller;

import com.example.java2project.domain.Developer;
import com.example.java2project.service.DeveloperService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/{repo}/developer")
public class DeveloperController {

    @Resource
    DeveloperService developerService;

    @RequestMapping("/count")
    public Integer developerCnt(@PathVariable("repo") Long repoId) {
        return developerService.countDeveloperByRepo(repoId);
    }

    @RequestMapping("/top5")
    public List<Developer> top5Developer(@PathVariable("repo") Long repoId) {
        return developerService.top5Developer(repoId);
    }

}
