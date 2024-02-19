package com.example.githubapi_project.users.service;

import lombok.Builder;

import java.util.List;

@Builder
public record RepositoryDTO(String repositoryName, String owner, List<BranchDTO> branches) {
    @Builder
    public record BranchDTO(String name, String lastCommitSha) {}
}
