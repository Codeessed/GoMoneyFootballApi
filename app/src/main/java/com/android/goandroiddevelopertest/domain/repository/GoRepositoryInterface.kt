package com.android.goandroiddevelopertest.domain.repository

import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.MatchResponseModel
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.db.entities.Match
import com.android.goandroiddevelopertest.db.entities.Squad
import com.android.goandroiddevelopertest.db.entities.Table
import com.android.goandroiddevelopertest.db.entities.Team
import kotlinx.coroutines.flow.Flow

interface GoRepositoryInterface {
    suspend fun getAllMatches(): Resource<MatchResponseModel>
    fun getAllCompetitions(refresh: Boolean = false): Flow<Resource<List<Competition>>>
    fun getAllTeams(competitionId: Int, refresh: Boolean = false): Flow<Resource<List<Team>>>
    fun getCompetitionMatches(competitionId: Int, refresh: Boolean = false): Flow<Resource<List<Match>>>
    fun getCompetitionTable(competitionId: Int, refresh: Boolean = false): Flow<Resource<List<Table>>>
    fun getTeamSquad(teamId: Int, refresh: Boolean = false): Flow<List<Squad>>
}