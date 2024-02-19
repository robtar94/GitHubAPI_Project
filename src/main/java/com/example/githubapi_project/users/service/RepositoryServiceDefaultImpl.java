package com.example.githubapi_project.users.service;

import com.example.githubapi_project.users.http.GitHubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepositoryServiceDefaultImpl implements RepositoryService {
    private final GitHubClient gitHubClient;

    @Override
    public List<RepositoryDTO> getUserRepositories(final String username) {
        var repos = gitHubClient.getUserRepositories(username);

        return repos.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> RepositoryDTO.builder()
                        .repositoryName(repo.name())
                        .branches(getRepositoryBranches(username, repo.name()))
                        .owner(repo.owner().login()).build())
                .collect(Collectors.toList());
    }

    private List<RepositoryDTO.BranchDTO> getRepositoryBranches(String username, String repositoryName) {
        var branches = gitHubClient.getRepositoryBranches(username, repositoryName);

        return branches.stream()
                .map(branch -> RepositoryDTO.BranchDTO.builder()
                        .name(branch.name())
                        .lastCommitSha(branch.commit().sha())
                        .build())
                .collect(Collectors.toList());
    }
}
