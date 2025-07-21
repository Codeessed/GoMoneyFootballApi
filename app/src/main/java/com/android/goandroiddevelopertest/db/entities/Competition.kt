package com.android.goandroiddevelopertest.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competition_table")
data class Competition(
    @PrimaryKey
    val competitionId: Int,
    val name: String
)
