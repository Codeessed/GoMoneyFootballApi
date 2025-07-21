package com.android.goandroiddevelopertest.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Table(
    @PrimaryKey
    val teamId: Int,
    val shortName: String,
    val crest: String,
    val position: Int,
    val playedGames: Int,
    val won: Int,
    val points: Int,
    val competitionId: Int,
)
