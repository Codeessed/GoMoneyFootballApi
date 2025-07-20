package com.android.goandroiddevelopertest.domain.repository

import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel

interface GoRepositoryInterface {
    suspend fun getAllMatches(): Resource<CompetitionResponseModel>
}