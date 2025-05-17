package edu.jgsilveira.tasks.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform