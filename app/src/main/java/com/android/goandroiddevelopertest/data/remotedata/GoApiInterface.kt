package com.android.goandroiddevelopertest.data.remotedata

import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface GoApiInterface {

//    @GET("matches")
//    suspend fun getAllMatches(
////        @Query("dateFrom") dateFrom: String = "2025-01-01",
////        @Query("dateTo") dateTo: String = "2025-07-19",
//        @Query("status") status: String = "SCHEDULED",
//    ): Response<String>

    @GET("competitions")
    suspend fun getAllMatches(
    ): Response<CompetitionResponseModel>

}