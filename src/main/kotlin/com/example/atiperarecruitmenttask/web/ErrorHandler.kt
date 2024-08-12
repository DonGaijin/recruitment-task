package com.example.atiperarecruitmenttask.web

import com.example.atiperarecruitmenttask.domain.exceptions.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException): ResponseEntity<Map<String, String>> {
        val errorResponse = mapOf(
            "status" to HttpStatus.NOT_FOUND.value().toString(),
            "message" to (ex.message ?: "User not found")
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
}