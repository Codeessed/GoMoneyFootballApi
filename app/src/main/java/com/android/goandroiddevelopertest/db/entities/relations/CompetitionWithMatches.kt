package com.android.goandroiddevelopertest.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.db.entities.Match
import com.android.goandroiddevelopertest.db.entities.Team

data class CompetitionWithMatches(
    @Embedded val competition: Competition,
    @Relation(
        parentColumn = "competitionId",
        entityColumn = "competitionId",
    )
    val matches: List<Match>

)
