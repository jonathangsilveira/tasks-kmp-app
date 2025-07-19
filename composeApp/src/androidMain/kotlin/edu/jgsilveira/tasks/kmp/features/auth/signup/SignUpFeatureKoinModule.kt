package edu.jgsilveira.tasks.kmp.features.auth.signup

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val signUpFeatureKoinModule = module {
    viewModel {
        SignUpViewModel(
            signUpUseCase = get()
        )
    }
}