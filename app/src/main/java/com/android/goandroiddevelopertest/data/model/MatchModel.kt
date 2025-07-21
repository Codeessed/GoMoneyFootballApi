package com.android.goandroiddevelopertest.data.model

data class MatchModel(
    val id: Int,
    val matchday: Int,
    val utcDate: String,
    val homeTeam: HomeTeamModel,
    val awayTeam: AwayTeamModel,
    val score: ScoreModel

)
