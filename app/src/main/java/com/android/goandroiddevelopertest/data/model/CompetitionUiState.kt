package com.android.goandroiddevelopertest.data.model

import com.android.goandroiddevelopertest.db.entities.Competition

data class CompetitionUiState(
    val users: List<Competition> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
