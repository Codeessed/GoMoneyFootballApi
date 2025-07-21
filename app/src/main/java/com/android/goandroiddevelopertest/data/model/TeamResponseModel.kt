package com.android.goandroiddevelopertest.data.model

import com.android.goandroiddevelopertest.db.entities.Competition

data class TeamResponseModel(
    val count: Int,
    val teams: List<TeamModel>
)
