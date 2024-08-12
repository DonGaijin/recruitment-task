package com.example.atiperarecruitmenttask.web

import com.example.atiperarecruitmenttask.application.GitHubService
import com.example.atiperarecruitmenttask.domain.exceptions.UserNotFoundException
import com.example.atiperarecruitmenttask.domain.model.GitHubRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(GitHubController::class)
class GitHubControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var gitHubService: GitHubService

    @Test
    fun `should return 200 and repositories for valid user`() {
        val username = "validUser"
        val repositories = listOf(
            GitHubRepository("repo1", "validUser", listOf())
        )
        whenever(gitHubService.getUserRepositories(username)).thenReturn(repositories)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/github/users/$username/repositories")
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").value("repo1"))
    }

    @Test
    fun `should return 404 for non-existing user`() {
        val username = "invalidUser"
        whenever(gitHubService.getUserRepositories(username)).thenThrow(UserNotFoundException::class.java)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/github/users/$username/repositories")
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `should return 406 for invalid Accept header`() {
        val username = "validUser"

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/github/users/$username/repositories")
                .header("Accept", "text/html")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotAcceptable)
    }
}
