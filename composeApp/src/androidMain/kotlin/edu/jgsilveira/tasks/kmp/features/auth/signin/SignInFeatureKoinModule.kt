package edu.jgsilveira.tasks.kmp.features.auth.signin

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val signInFeatureKoinModule = module {
    viewModel {
        SignInViewModel(signInUseCase = get())
    }
}