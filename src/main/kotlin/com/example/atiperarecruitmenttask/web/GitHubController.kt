package com.example.atiperarecruitmenttask.web

import com.example.atiperarecruitmenttask.application.GitHubService
import com.example.atiperarecruitmenttask.domain.model.GitHubRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/github")
class GitHubController(private val gitHubService: GitHubService) {

    @GetMapping("/users/{username}/repositories")
    fun getRepositories(
        @PathVariable username: String,
        @RequestHeader("Accept") acceptHeader: String
    ): ResponseEntity<List<GitHubRepository>> {
        if (acceptHeader != "application/json") {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build()
        }

        val repositories = gitHubService.getUserRepositories(username)
        return ResponseEntity.ok(repositories)
    }
}