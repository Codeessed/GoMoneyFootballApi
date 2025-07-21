package com.android.goandroiddevelopertest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.db.entities.Match
import com.android.goandroiddevelopertest.db.entities.Squad
import com.android.goandroiddevelopertest.db.entities.Standing
import com.android.goandroiddevelopertest.db.entities.Table
import com.android.goandroiddevelopertest.db.entities.Team
import com.android.goandroiddevelopertest.db.entities.relations.CompetitionTeamCrossRef
import com.android.goandroiddevelopertest.db.entities.relations.CompetitionWithMatches
import com.android.goandroiddevelopertest.db.entities.relations.CompetitionWithTable
import com.android.goandroiddevelopertest.db.entities.relations.CompetitionWithTeams
import com.android.goandroiddevelopertest.db.entities.relations.TeamWithSquads
//import com.android.goandroiddevelopertest.db.entities.relations.CompetitionWithTeams
import kotlinx.coroutines.flow.Flow

@Dao
interface GoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCompetitions(competitions: List<Competition>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTeams(teams: List<Team>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStandings(standings: List<Table>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<Match>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquads(squads: List<Squad>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetitionTeamCrossRef(crossRef: List<CompetitionTeamCrossRef>)

    @Query("SELECT * FROM competition_table ORDER BY name ASC")
    fun getAllCompetitions(): Flow<List<Competition>>

    @Query("SELECT * FROM competition_table where competitionId = :competitionId")
    fun getCompetitionWithTable(competitionId: Int): Flow<CompetitionWithTable>

    @Query("SELECT * FROM team_table where teamId = :teamId")
    fun getTeamWithSquad(teamId: Int): Flow<TeamWithSquads>

    @Query("SELECT * FROM competition_table where competitionId = :competitionId")
    fun getCompetitionWithMatches(competitionId: Int): Flow<CompetitionWithMatches>

    @Query("SELECT * FROM competition_table where competitionId = :competitionId")
    fun getTeamsOfCompetition(competitionId: Int): Flow<CompetitionWithTeams>

}