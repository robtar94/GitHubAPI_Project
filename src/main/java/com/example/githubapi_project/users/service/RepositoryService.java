package com.example.githubapi_project.users.service;

import org.springframework.stereotype.Service;

import java.util.List;

public interface RepositoryService {
    List<RepositoryDTO> getUserRepositories(final String username);
}
