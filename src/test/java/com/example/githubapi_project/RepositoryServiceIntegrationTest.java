package com.example.githubapi_project;

import com.example.githubapi_project.users.service.RepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWireMock
class RepositoryServiceIntegrationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    void givenUsername_whenGetUserRepositoriesIsCalled_thenReturnUserRepositoriesWithBranches() {
        //given
        var username = "test";
        var testRepositoryName = "Test";

        stubFor(get(String.format("/users/%s/repos", username))
                .willReturn(aResponse()
                        .withBodyFile("user_repos.json")
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));

        stubFor(get(String.format("/repos/%s/%s/branches", username, testRepositoryName))
                .willReturn(aResponse()
                        .withBodyFile("test_repo_branches.json")
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));

        //when
        var repositories = repositoryService.getUserRepositories(username);

        //then
        assertThat(repositories).isNotNull();
        assertThat(repositories).isNotEmpty();
    }
}
