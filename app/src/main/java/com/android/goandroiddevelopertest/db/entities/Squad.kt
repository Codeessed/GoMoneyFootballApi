package com.android.goandroiddevelopertest.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "squad_table")
data class Squad(
    @PrimaryKey
    val squadId: Int,
    val name: String,
    val position: String,
    val teamId: Int,
)
