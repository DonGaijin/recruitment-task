package com.example.atiperarecruitmenttask.domain.model

data class GitHubRepository(
    val name: String,
    val ownerLogin: String,
    val branches: List<GitHubBranch>
)

data class GitHubBranch(
    val name: String,
    val lastCommitSha: String
)