package com.android.goandroiddevelopertest.data.remotedata

import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import com.android.goandroiddevelopertest.data.model.MatchResponseModel
import com.android.goandroiddevelopertest.data.model.StandingResponseModel
import com.android.goandroiddevelopertest.data.model.TeamModel
import com.android.goandroiddevelopertest.data.model.TeamResponseModel
import com.android.goandroiddevelopertest.db.entities.Team
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GoApiInterface {

    @GET("matches")
    suspend fun getAllMatches(
    ): Response<MatchResponseModel>

    @GET("competitions")
    suspend fun getAllCompetitions(
    ): Response<CompetitionResponseModel>

    @GET("competitions/{id}/teams")
    suspend fun getAllTeams(
        @Path("id")
        id: Int
    ): Response<TeamResponseModel>

    @GET("competitions/{id}/matches")
    suspend fun getCompetitionMatches(
        @Path("id")
        id: Int
    ): Response<MatchResponseModel>

    @GET("competitions/{id}/standings")
    suspend fun getCompetitionStandings(
        @Path("id")
        id: Int
    ): Response<StandingResponseModel>

}