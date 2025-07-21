package com.android.goandroiddevelopertest.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.db.entities.Team

data class CompetitionWithTeams(
    @Embedded val competition: Competition,
    @Relation(
        parentColumn = "competitionId",
        entityColumn = "teamId",
        associateBy = Junction(CompetitionTeamCrossRef::class)
    )
    val teams: List<Team>

)
