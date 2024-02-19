package com.example.githubapi_project.users.controller;

import com.example.githubapi_project.users.service.RepositoryDTO;
import com.example.githubapi_project.users.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final RepositoryService repositoryService;

    @GetMapping("/users/{username}/repos")
    public List<RepositoryDTO> getUserRepositories(@PathVariable final String username) {
        return repositoryService.getUserRepositories(username);
    }
}
