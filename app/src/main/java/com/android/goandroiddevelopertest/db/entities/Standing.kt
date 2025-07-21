package com.android.goandroiddevelopertest.db.entities

data class Standing(
    val position: Int,
    val playedGames: Int,
    val teamId: Int,
    val won: Int,
    val points: Int,
)
