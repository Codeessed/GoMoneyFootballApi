package com.android.goandroiddevelopertest.domain.repository

import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import com.android.goandroiddevelopertest.data.remotedata.GoApiInterface
import com.google.gson.Gson
import java.lang.Exception
import javax.inject.Inject


class GoRepositoryImpl @Inject constructor(
    private val goApiInterface: GoApiInterface
): GoRepositoryInterface {
    override suspend fun getAllMatches(): Resource<CompetitionResponseModel> {
        val response = goApiInterface.getAllMatches()
        val result = response.body()
        return try {
            when(response.code()){
                in 200..299 -> Resource.Success(result)
                in 400..499 ->{
//                    val gson = Gson()
//                    val error: ErrorMessages = gson.fromJson(response.errorBody()?.charStream(), ErrorMessages::class.java)
                    Resource.Error("error")
                }
                else -> {
                    Resource.Error(response.message())
                }
            }
        }catch (e: Exception){
            Resource.Error("An error occurred")
        }
    }
}