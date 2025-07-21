package com.android.goandroiddevelopertest.data.model

import com.android.goandroiddevelopertest.db.entities.Competition

data class CompetitionResponseModel(
    val count: Int,
    val competitions: List<CompetitionModel>
)
