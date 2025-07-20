package com.android.goandroiddevelopertest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import com.android.goandroiddevelopertest.domain.repository.GoRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GoAndroidViewModel @Inject constructor(
    private val goRepositoryInterface: GoRepositoryInterface
): ViewModel() {

    sealed class GoEvent{
        class MatchesSuccessEvent(val matchesResult: CompetitionResponseModel): GoEvent()
        class Error(val errorText: String): GoEvent()
        object Loading: GoEvent()
        object Empty: GoEvent()
    }

    private var _allMatches = MutableStateFlow<GoEvent>(GoEvent.Empty)
    val allMatches get() = _allMatches.asStateFlow()

    fun getAllMatches(){
        viewModelScope.launch {
            _allMatches.value = GoEvent.Loading
            try {
                when(val getMatchesResponse = goRepositoryInterface.getAllMatches()){
                    is Resource.Success -> {
                        _allMatches.value = GoEvent.MatchesSuccessEvent(getMatchesResponse.data!!)
                    }
                    is Resource.Error -> _allMatches.value = GoEvent.Error(getMatchesResponse.message!!)
                    else -> Unit
                }
            }catch (e: Exception){
                when(e){
                    is IOException -> _allMatches.value = GoEvent.Error("Weak Network")
                    else -> _allMatches.value = GoEvent.Error(e.message!!)
                }
            }
        }
    }


}