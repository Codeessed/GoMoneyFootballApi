package com.android.goandroiddevelopertest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.MatchResponseModel
import com.android.goandroiddevelopertest.data.model.RefreshTeamsModel
import com.android.goandroiddevelopertest.db.entities.Competition
import com.android.goandroiddevelopertest.db.entities.Match
import com.android.goandroiddevelopertest.db.entities.Squad
import com.android.goandroiddevelopertest.db.entities.Table
import com.android.goandroiddevelopertest.db.entities.Team
import com.android.goandroiddevelopertest.domain.repository.GoRepositoryInterface
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GoAndroidViewModel @Inject constructor(
    private val goRepositoryInterface: GoRepositoryInterface,
): ViewModel() {

    private val _selectedCompetitionId = MutableStateFlow<RefreshTeamsModel>(RefreshTeamsModel(0, false))
    private val selectedCompetitionId = _selectedCompetitionId.asStateFlow()

    private val _selectedTeam = MutableStateFlow(Team(0, "", ""))
    val selectedTeam = _selectedTeam.asStateFlow()

    private val _allMatchesState = MutableStateFlow<GoEvent>(GoEvent.Empty)
    val allMatchesState = _allMatchesState.asStateFlow()

    sealed class GoEvent {
        class AllMatchesSuccessEvent(val result: MatchResponseModel) : GoEvent()
        class Error(val error: String) : GoEvent()
        object Loading : GoEvent()
        object Empty : GoEvent()
    }

    fun getAllMatches(){
        viewModelScope.launch {
            _allMatchesState.value = GoEvent.Loading
            try {
                when(val allMatchResponse = goRepositoryInterface.getAllMatches()){
                    is Resource.Success -> _allMatchesState.value = GoEvent.AllMatchesSuccessEvent(allMatchResponse.data!!)
                    is Resource.Error -> _allMatchesState.value = GoEvent.Error(allMatchResponse.message!!)
                    else -> Unit
                }
            }catch (e: Exception){
                when(e){
                    is IOException -> _allMatchesState.value = GoEvent.Error("Weak Network")
                    else -> _allMatchesState.value = GoEvent.Error(e.message!!)
                }
            }
        }
    }

    private val refreshAllCompetitions = MutableSharedFlow<Boolean>(0)

    val allCompetitions: StateFlow<Resource<List<Competition>>> = refreshAllCompetitions.
        onStart { emit(false) }
        .flatMapLatest { refresh ->
            goRepositoryInterface.getAllCompetitions(refresh)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Empty
        )

    val allTeams: StateFlow<Resource<List<Team>>> = selectedCompetitionId.
    onStart { emit(RefreshTeamsModel(0, false)) }
        .flatMapLatest { _ ->
            goRepositoryInterface.getAllTeams(selectedCompetitionId.value.competitionId, selectedCompetitionId.value.refresh)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Empty
        )

    val competitionMatches: StateFlow<Resource<List<Match>>> = selectedCompetitionId.
    onStart { emit(RefreshTeamsModel(0, false)) }
        .flatMapLatest { _ ->
            goRepositoryInterface.getCompetitionMatches(selectedCompetitionId.value.competitionId, selectedCompetitionId.value.refresh)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Empty
        )

    val allStandings: StateFlow<Resource<List<Table>>> = selectedCompetitionId.
    onStart { emit(RefreshTeamsModel(0, false)) }
        .flatMapLatest { _ ->
            goRepositoryInterface.getCompetitionTable(selectedCompetitionId.value.competitionId, selectedCompetitionId.value.refresh)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Empty
        )

    val teamSquads: StateFlow<List<Squad>> = selectedTeam.
    onStart { emit(Team(0, "", "")) }
        .flatMapLatest { team ->
            goRepositoryInterface.getTeamSquad(team.teamId, false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = listOf()
        )

    fun refreshAllCompetitions(){
        viewModelScope.launch {
            refreshAllCompetitions.emit(true)
        }
    }

    fun updatedSelectedCompetitionId(competitionId: Int, refresh: Boolean = false){
        viewModelScope.launch {
            _selectedCompetitionId.value = RefreshTeamsModel(competitionId, refresh)
        }
    }

    fun updatedSelectedTeam(team: Team){
        viewModelScope.launch {
            _selectedTeam.value = team
        }
    }


}