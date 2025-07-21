package com.android.goandroiddevelopertest.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.db.entities.Squad
import com.android.goandroiddevelopertest.db.entities.Team

data class TeamWithSquads(
    @Embedded val team: Team,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "teamId",
    )
    val squads: List<Squad>

)
