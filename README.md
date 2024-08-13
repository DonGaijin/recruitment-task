# atipera-recruitment-task
# GitHub Repository Browser API

This is a simple REST API that allows consumers to retrieve a list of GitHub repositories for a specific user. It filters out forked repositories and provides information about the repository name, owner login, and for each branch, its name and the last commit SHA.

## Features

- Retrieve all non-fork repositories of a specified GitHub user.
- For each repository, fetch its name, owner login, and branches with their last commit SHA.
- Return a 404 response in a specific format when the GitHub user does not exist.

## API Documentation

### Get User Repositories

**Endpoint:** `GET /api/github/users/{username}/repositories`

**Headers:**
- `Accept: application/json`

**Path Parameters:**
- `username`: The GitHub username for which to fetch repositories.

**Response:**

- **200 OK**: A JSON array of repositories.
- **404 Not Found**: If the user does not exist.

**Success Response Example:**
```json
[
    {
        "repositoryName": "example-repo",
        "ownerLogin": "aaasssddd",
        "branches": [
            {
                "name": "main",
                "lastCommitSha": "abc123def456"
            },
            {
                "name": "develop",
                "lastCommitSha": "789xyz654abc"
            }
        ]
    }
]
