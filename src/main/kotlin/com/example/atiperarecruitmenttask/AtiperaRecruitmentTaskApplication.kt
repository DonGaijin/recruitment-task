package com.example.atiperarecruitmenttask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class AtiperaRecruitmentTaskApplication

fun main(args: Array<String>) {
    runApplication<AtiperaRecruitmentTaskApplication>(*args)
}
