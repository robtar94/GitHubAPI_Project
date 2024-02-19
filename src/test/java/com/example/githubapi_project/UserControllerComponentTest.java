package com.example.githubapi_project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock
public class UserControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenUsername_whenGettingExistingUserRepositories_thenShouldReturnRepositoriesWithBranches() throws Exception {
        mockMvc.perform(get("/users/{username}/repos", TestData.USERNAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].repositoryName").value(TestData.FIRST_REPO_NAME))
                .andExpect(jsonPath("$[0].owner").value(TestData.OWNER_NAME))
                .andExpect(jsonPath("$[0].branches[0].name").value(TestData.FIRST_BRANCH_NAME))
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").value(TestData.FIRST_BRANCH_LAST_COMMIT))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUsername_whenGettingNonExistingUserRepositories_thenShouldReturn404NotFoundCodeWithMessage() throws Exception {
        mockMvc.perform(get("/users/{username}/repos", TestData.NON_EXISTING_USERNAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.message").value("Specified user has been not found"));
    }
}