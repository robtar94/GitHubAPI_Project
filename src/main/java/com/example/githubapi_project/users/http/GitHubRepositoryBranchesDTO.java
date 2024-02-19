package com.example.githubapi_project.users.http;
import lombok.Builder;

@Builder
public record GitHubRepositoryBranchesDTO(String name, GitHubRepositoryCommitDTO commit) {
    @Builder
    public record GitHubRepositoryCommitDTO(String sha) {

    }
}
