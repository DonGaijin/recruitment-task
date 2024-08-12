package com.example.atiperarecruitmenttask.application

import com.example.atiperarecruitmenttask.domain.exceptions.UserNotFoundException
import com.example.atiperarecruitmenttask.domain.model.GitHubBranch
import com.example.atiperarecruitmenttask.domain.model.GitHubRepository
import com.example.atiperarecruitmenttask.infrastructure.client.GitHubClient
import org.springframework.stereotype.Service

@Service
class GitHubService(private val gitHubClient: GitHubClient) {

    fun getUserRepositories(username: String): List<GitHubRepository> {
        val repositories = try {
            gitHubClient.getRepos(username)
        } catch (ex: Exception) {
            throw UserNotFoundException("User $username not found.")
        }

        return repositories
            .filter { !it.fork }
            .map { repo ->
                val branches = gitHubClient.getBranches(repo.owner.login, repo.name)
                    .map { branch -> GitHubBranch(branch.name, branch.commit.sha) }

                GitHubRepository(repo.name, repo.owner.login, branches)
            }
    }
}