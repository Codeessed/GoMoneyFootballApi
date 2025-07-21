package com.android.goandroiddevelopertest.data.model

data class TableModel(
    val position: Int,
    val playedGames: Int,
    val team: TeamModel,
    val won: Int,
    val points: Int,
)
