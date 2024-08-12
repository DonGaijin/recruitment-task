package com.example.atiperarecruitmenttask.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "githubClient", url = "https://api.github.com")
interface GitHubClient {

    @GetMapping("/users/{user}/repos")
    fun getRepos(@PathVariable("user") user: String): List<GitHubRepositoryResponse>

    @GetMapping("/repos/{owner}/{repo}/branches")
    fun getBranches(@PathVariable("owner") owner: String, @PathVariable("repo") repo: String): List<GitHubBranchResponse>
}

data class GitHubRepositoryResponse(
    val name: String,
    val owner: GitHubOwnerResponse,
    val fork: Boolean
)

data class GitHubOwnerResponse(
    val login: String
)

data class GitHubBranchResponse(
    val name: String,
    val commit: GitHubCommitResponse
)

data class GitHubCommitResponse(
    val sha: String
)