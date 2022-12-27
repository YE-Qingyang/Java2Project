package com.example.java2project.controller;

import com.example.java2project.domain.Repository;
import com.example.java2project.service.RepositoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repo")
public class RepositoryController {

    @Resource
    private RepositoryService repositoryService;

    @RequestMapping("/update_data")
    public boolean updateData() {
        return repositoryService.updateData();
    }

    @RequestMapping("/all_repo")
    public List<Repository> getAllRepository() {
        return repositoryService.getRepo();
    }
}
