package com.android.goandroiddevelopertest.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_table")
data class Match(
    @PrimaryKey
    val id: Int,
    val matchday: Int,
    val utcDate: String,
    val homeTeamName: String,
    val awayTeamName: String,
    val homeTeamScore: Int,
    val awayTeamScore: Int,
    val competitionId: Int,
)
