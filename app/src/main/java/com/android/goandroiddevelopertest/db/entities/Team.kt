package com.android.goandroiddevelopertest.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class Team(
    @PrimaryKey
    val teamId: Int,
    val shortName: String,
    val crest: String
)
