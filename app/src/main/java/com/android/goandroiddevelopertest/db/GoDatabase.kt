package com.android.goandroiddevelopertest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.db.entities.Match
import com.android.goandroiddevelopertest.db.entities.Squad
import com.android.goandroiddevelopertest.db.entities.Table
import com.android.goandroiddevelopertest.db.entities.Team
import com.android.goandroiddevelopertest.db.entities.relations.CompetitionTeamCrossRef

@Database(
    entities = [
        Competition::class,
        Team::class,
        Squad::class,
        CompetitionTeamCrossRef::class,
        Match::class,
        Table::class
               ],
    version = 1
)
abstract  class GoDatabase: RoomDatabase() {

    abstract val dao: GoDao

}