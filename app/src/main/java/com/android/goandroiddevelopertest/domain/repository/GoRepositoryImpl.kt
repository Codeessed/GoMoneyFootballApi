package com.android.goandroiddevelopertest.domain.repository

import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.CompetitionModel
import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import com.android.goandroiddevelopertest.data.remotedata.GoApiInterface
import com.android.goandroiddevelopertest.db.GoDao
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import java.lang.Exception
import javax.inject.Inject


class GoRepositoryImpl @Inject constructor(
    private val goApiInterface: GoApiInterface,
    private val goDao: GoDao
): GoRepositoryInterface {

    override fun getAllMatches(refresh: Boolean): Flow<Resource<List<CompetitionModel>>> = flow {

        emit(Resource.Loading)

        val dbMatches = goDao.getAllCompetitions().first()

        if (refresh || dbMatches.isEmpty()) {
            try {
                val remoteData = goApiInterface.getAllMatches()
                val competitionList = remoteData.body()?.competitions ?: emptyList()
                goDao.insertAllCompetitions(competitionList)
                emitAll(goDao.getAllCompetitions().map { Resource.Success(it) })
            } catch (e: Exception) {
                emit(Resource.Error(dbMatches,e.localizedMessage ?: "Unknown error"))
            }
        } else {
            emitAll(goDao.getAllCompetitions().map { Resource.Success(it) })
        }

    }

}