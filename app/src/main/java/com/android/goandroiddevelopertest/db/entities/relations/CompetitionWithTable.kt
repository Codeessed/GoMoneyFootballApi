package com.android.goandroiddevelopertest.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.db.entities.Table
import com.android.goandroiddevelopertest.db.entities.Team

data class CompetitionWithTable(
    @Embedded val competition: Competition,
    @Relation(
        parentColumn = "competitionId",
        entityColumn = "competitionId",
    )
    val table: List<Table>

)
