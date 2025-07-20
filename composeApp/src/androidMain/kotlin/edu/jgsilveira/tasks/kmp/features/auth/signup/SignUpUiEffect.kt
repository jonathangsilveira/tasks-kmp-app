package edu.jgsilveira.tasks.kmp.features.auth.signup

import edu.jgsilveira.tasks.kmp.core.arch.viewmodel.UIEffect

internal sealed interface SignUpUiEffect : UIEffect {
    data object NavigateUp : SignUpUiEffect
}