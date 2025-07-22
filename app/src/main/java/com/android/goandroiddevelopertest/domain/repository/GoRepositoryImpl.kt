package com.android.goandroiddevelopertest.domain.repository

import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.MatchResponseModel
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.data.remotedata.GoApiInterface
import com.android.goandroiddevelopertest.db.GoDao
import com.android.goandroiddevelopertest.db.entities.Match
import com.android.goandroiddevelopertest.db.entities.Squad
import com.android.goandroiddevelopertest.db.entities.Table
import com.android.goandroiddevelopertest.db.entities.Team
import com.android.goandroiddevelopertest.db.entities.relations.CompetitionTeamCrossRef
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject


class GoRepositoryImpl @Inject constructor(
    private val goApiInterface: GoApiInterface,
    private val goDao: GoDao
): GoRepositoryInterface {

    override suspend fun getAllMatches(): Resource<MatchResponseModel> {
        val response = goApiInterface.getAllMatches()
        val result = response.body()
        return try {
            when(response.code()){
                in 200..299 -> Resource.Success(result)
                in 400..499 -> {
                    Resource.Error(null,"There was an error")
                }
                else -> Resource.Error(null, response.message())
            }
        }catch (e: Exception){
            Resource.Error(null, "An error occurred")
        }
    }

    override fun getAllCompetitions(refresh: Boolean): Flow<Resource<List<Competition>>> = flow {

        emit(Resource.Loading)

        val dbMatches = goDao.getAllCompetitions().first()

        if (refresh || dbMatches.isEmpty()) {
            try {
                val remoteData = goApiInterface.getAllCompetitions()
                val competitionList = remoteData.body()?.competitions ?: emptyList()
                goDao.insertAllCompetitions(
                    competitionList.map {
                        Competition(
                            competitionId = it.id,
                            name = it.name
                        )
                    }
                )
                emitAll(
                    goDao.getAllCompetitions().map { Resource.Success(it) }
                )
            } catch (e: Exception) {
                emit(Resource.Error(dbMatches,e.localizedMessage ?: "Unknown error"))
            }
        } else {
            emit(Resource.Success(dbMatches))
        }

    }

    override fun getAllTeams(competitionId: Int, refresh: Boolean): Flow<Resource<List<Team>>> = flow {

        emit(Resource.Loading)

        val dbTeams = goDao.getTeamsOfCompetition(competitionId).first().teams

        if (refresh || dbTeams.isEmpty()) {
            try {
                val remoteData = goApiInterface.getAllTeams(competitionId)
                val teamList = remoteData.body()?.teams ?: emptyList()
                goDao.insertSquads(
                    teamList.flatMap { team ->
                        team.squad!!.map { member ->
                            Squad(
                                squadId = member.id,
                                name = member.name,
                                position = member.position,
                                teamId = team.id

                            )
                        }
                    }
                )

                goDao.insertAllTeams(
                    teamList.map {
                        Team(
                            teamId = it.id,
                            shortName = it.shortName ?: "",
                            crest = it.crest
                        )
                    }
                )
                goDao.insertCompetitionTeamCrossRef(
                    teamList.map {
                        CompetitionTeamCrossRef(
                            competitionId = competitionId,
                            teamId = it.id
                        )
                    }
                )
                emitAll(
                    goDao.getTeamsOfCompetition(competitionId).map { Resource.Success(it.teams) }
                )
            } catch (e: Exception) {
                emit(Resource.Error(dbTeams,e.localizedMessage ?: "Unknown error"))
            }
        } else {
            emit(Resource.Success(dbTeams))
        }
    }

    override fun getCompetitionMatches(
        competitionId: Int,
        refresh: Boolean
    ): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading)

        val dbMatches = goDao.getCompetitionWithMatches(competitionId).first().matches

        if (refresh || dbMatches.isEmpty()) {
            try {
                val remoteData = goApiInterface.getCompetitionMatches(competitionId)
                val matchList = remoteData.body()?.matches ?: emptyList()
                goDao.insertMatches(
                    matchList.map {
                        Match(
                            id = it.id,
                            matchday = it.matchday,
                            utcDate = it.utcDate,
                            homeTeamName = it.homeTeam.shortName,
                            awayTeamName = it.awayTeam.shortName ?: "",
                            homeTeamScore = it.score.fullTime.home,
                            awayTeamScore = it.score.fullTime.away,
                            competitionId = competitionId
                        )
                    }
                )
                emitAll(
                    goDao.getCompetitionWithMatches(competitionId).map { Resource.Success(it.matches) }
                )
            } catch (e: Exception) {
                emit(Resource.Error(dbMatches,e.localizedMessage ?: "Unknown error"))
            }
        } else {
            emit(Resource.Success(dbMatches))
        }
    }

    override fun getCompetitionTable(
        competitionId: Int,
        refresh: Boolean
    ): Flow<Resource<List<Table>>> = flow {

        val dbTables = goDao.getCompetitionWithTable(competitionId).first().table

        if (refresh || dbTables.isEmpty()) {
            try {
                val remoteData = goApiInterface.getCompetitionStandings(competitionId)
                val standingList = remoteData.body()?.standings?.first()?.table ?: emptyList()
                goDao.insertStandings(
                    standingList.map {
                        Table(
                            teamId = it.team.id,
                            shortName = it.team.shortName ?: "",
                            crest = it.team.crest,
                            position = it.position,
                            playedGames = it.playedGames,
                            won = it.won,
                            points = it.points,
                            competitionId = competitionId
                        )
                    }
                )
                emitAll(
                    goDao.getCompetitionWithTable(competitionId).map { Resource.Success(it.table) }
                )
            } catch (e: Exception) {
                emit(Resource.Error(dbTables,e.localizedMessage ?: "Unknown error"))
            }
        } else {
            emit(Resource.Success(dbTables))
        }

    }

    override fun getTeamSquad(teamId: Int, refresh: Boolean): Flow<List<Squad>> = flow {

        emitAll(
            goDao.getTeamWithSquad(teamId).map { it.squads}
        )

    }

}