package com.android.goandroiddevelopertest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competition_table")
data class CompetitionModel(
    @PrimaryKey
    val id: Int,
    val name: String
)
