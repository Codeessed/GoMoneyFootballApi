package com.android.goandroiddevelopertest.domain.repository

import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.CompetitionModel
import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import kotlinx.coroutines.flow.Flow

interface GoRepositoryInterface {
    fun getAllMatches(refresh: Boolean = false): Flow<Resource<List<CompetitionModel>>>
}