package com.android.goandroiddevelopertest.db.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["competitionId", "teamId"])
data class CompetitionTeamCrossRef(
    val competitionId: Int,
    val teamId: Int

)
