package com.android.goandroiddevelopertest.data.model

data class CompetitionUiState(
    val users: List<CompetitionModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
