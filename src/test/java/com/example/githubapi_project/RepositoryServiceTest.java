package com.example.githubapi_project;

import com.example.githubapi_project.users.http.GitHubClient;
import com.example.githubapi_project.users.http.GitHubRepositoryDTO;
import com.example.githubapi_project.users.http.GitHubRepositoryBranchesDTO;
import com.example.githubapi_project.users.service.RepositoryDTO;
import com.example.githubapi_project.users.service.RepositoryService;
import com.example.githubapi_project.users.service.RepositoryServiceDefaultImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RepositoryServiceTest {

    private GitHubClient gitHubClient;
    private RepositoryService service;

    @BeforeEach
    public void setup() {
        gitHubClient = Mockito.mock(GitHubClient.class);
        this.service = new RepositoryServiceDefaultImpl(gitHubClient);
    }

    @Test
    public void givenUsername_whenFetchingForUserRepositories_thenReturnUserRepositoriesThatAreNotForksWithBranches() {
        //given
        var repo = GitHubRepositoryDTO.builder().name(TestData.FIRST_REPO_NAME).owner(GitHubRepositoryDTO.GitHubRepositoryOwnerDTO.builder().login(TestData.OWNER_NAME).build()).fork(false).build();
        var secondRepo = GitHubRepositoryDTO.builder().name(TestData.SECOND_REPO_NAME).owner(GitHubRepositoryDTO.GitHubRepositoryOwnerDTO.builder().login(TestData.OWNER_NAME).build()).fork(true).build();
        var firstBranch = GitHubRepositoryBranchesDTO.builder().name(TestData.FIRST_BRANCH_NAME).commit(GitHubRepositoryBranchesDTO.GitHubRepositoryCommitDTO.builder().sha(TestData.FIRST_BRANCH_LAST_COMMIT).build()).build();
        var secondBranch = GitHubRepositoryBranchesDTO.builder().name(TestData.SECOND_BRANCH_NAME).commit(GitHubRepositoryBranchesDTO.GitHubRepositoryCommitDTO.builder().sha(TestData.SECOND_BRANCH_LAST_COMMIT).build()).build();

        //when
        when(gitHubClient.getUserRepositories(TestData.USERNAME)).thenReturn(List.of(repo, secondRepo));
        when(gitHubClient.getRepositoryBranches(TestData.USERNAME, TestData.FIRST_REPO_NAME)).thenReturn(List.of(firstBranch, secondBranch));

        //then
        List<RepositoryDTO> repositories = service.getUserRepositories(TestData.USERNAME);

        assertThat(repositories).hasSize(1);
        assertThat(repositories.getFirst().repositoryName()).isEqualTo(TestData.FIRST_REPO_NAME);
        assertThat(repositories.getFirst().owner()).isEqualTo(TestData.OWNER_NAME);
        assertThat(repositories.getFirst().branches()).extracting(RepositoryDTO.BranchDTO::name).containsExactlyInAnyOrder(TestData.FIRST_BRANCH_NAME, TestData.SECOND_BRANCH_NAME);
        assertThat(repositories.getFirst().branches()).extracting(RepositoryDTO.BranchDTO::lastCommitSha).containsExactlyInAnyOrder(TestData.FIRST_BRANCH_LAST_COMMIT, TestData.SECOND_BRANCH_LAST_COMMIT);

        verify(gitHubClient, times(1)).getUserRepositories(TestData.USERNAME);
        verify(gitHubClient, times(1)).getRepositoryBranches(TestData.USERNAME, TestData.FIRST_REPO_NAME);
    }
}