package edu.jgsilveira.tasks.kmp.auth.di

import edu.jgsilveira.tasks.kmp.auth.data.remote.service.AuthApiServiceImpl
import edu.jgsilveira.tasks.kmp.auth.domain.repository.AuthRepositoryImpl
import edu.jgsilveira.tasks.kmp.auth.domain.usecase.SignInUseCase
import edu.jgsilveira.tasks.kmp.auth.domain.usecase.SignUpUseCase
import edu.jgsilveira.tasks.kmp.createHttpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val domainAuthKoinModule = module {
    single<HttpClient> { createHttpClient() }

    factory<SignUpUseCase> {
        SignUpUseCase(
            authRepository = AuthRepositoryImpl(
                apiService = AuthApiServiceImpl(
                    client = get()
                )
            )
        )
    }

    factory<SignInUseCase> {
        SignInUseCase(
            authRepository = AuthRepositoryImpl(
                apiService = AuthApiServiceImpl(
                    client = get()
                )
            )
        )
    }
}