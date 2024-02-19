package com.example.githubapi_project.users.http;


import lombok.Builder;

@Builder
public record GitHubRepositoryDTO(String name, GitHubRepositoryOwnerDTO owner, boolean fork) {

    @Builder
    public record GitHubRepositoryOwnerDTO(String login) {

    }
}
