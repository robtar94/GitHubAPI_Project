package com.example.githubapi_project.users.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "github", url = "${github.base.url}")
public interface GitHubClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}/repos")
    List<GitHubRepositoryDTO> getUserRepositories(@PathVariable("username") final String username);

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{username}/{repositoryName}/branches")
    List<GitHubRepositoryBranchesDTO> getRepositoryBranches(@PathVariable("username") final String username,
                                                            @PathVariable("repositoryName") final String repositoryName);


}
