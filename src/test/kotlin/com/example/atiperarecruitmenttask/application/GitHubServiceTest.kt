package com.example.atiperarecruitmenttask.application

import com.example.atiperarecruitmenttask.domain.exceptions.UserNotFoundException
import com.example.atiperarecruitmenttask.infrastructure.client.GitHubBranchResponse
import com.example.atiperarecruitmenttask.infrastructure.client.GitHubClient
import com.example.atiperarecruitmenttask.infrastructure.client.GitHubCommitResponse
import com.example.atiperarecruitmenttask.infrastructure.client.GitHubOwnerResponse
import com.example.atiperarecruitmenttask.infrastructure.client.GitHubRepositoryResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class GitHubServiceTest {

    private val gitHubClient = Mockito.mock(GitHubClient::class.java)
    private val gitHubService = GitHubService(gitHubClient)

    @Test
    fun `should return list of repositories for valid user`() {
        val username = "validUser"
        val repoResponse = listOf(
            GitHubRepositoryResponse("repo1", GitHubOwnerResponse("validUser"), false),
            GitHubRepositoryResponse("repo2", GitHubOwnerResponse("validUser"), false)
        )
        whenever(gitHubClient.getRepos(username)).thenReturn(repoResponse)
        whenever(gitHubClient.getBranches("validUser", "repo1")).thenReturn(
            listOf(GitHubBranchResponse("main", GitHubCommitResponse("commitSha1")))
        )
        whenever(gitHubClient.getBranches("validUser", "repo2")).thenReturn(
            listOf(GitHubBranchResponse("main", GitHubCommitResponse("commitSha2")))
        )

        val repositories = gitHubService.getUserRepositories(username)

        assertEquals(2, repositories.size)
        assertEquals("repo1", repositories[0].name)
        assertEquals("commitSha1", repositories[0].branches[0].lastCommitSha)
    }

    @Test
    fun `should throw UserNotFoundException for invalid user`() {
        val username = "invalidUser"
        whenever(gitHubClient.getRepos(username)).thenThrow(UserNotFoundException::class.java)

        assertThrows(UserNotFoundException::class.java) {
            gitHubService.getUserRepositories(username)
        }
    }
}
