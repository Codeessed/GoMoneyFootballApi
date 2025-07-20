package com.android.goandroiddevelopertest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goandroiddevelopertest.Resource
import com.android.goandroiddevelopertest.data.model.CompetitionModel
import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import com.android.goandroiddevelopertest.db.GoDao
import com.android.goandroiddevelopertest.domain.repository.GoRepositoryImpl
import com.android.goandroiddevelopertest.domain.repository.GoRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GoAndroidViewModel @Inject constructor(
    private val goRepositoryInterface: GoRepositoryInterface,
): ViewModel() {

//    private val _allMatchesState = Channel<GoEvent>()
//    val allMatchesState = _allMatchesState.receiveAsFlow()

    sealed class GoEvent{

        class MatchesSuccessEvent(): GoEvent()
        class Error(val message: String): GoEvent()
        object Loading: GoEvent()
    }

    private val refreshAllMatches = MutableSharedFlow<Boolean>(0)

    val allMatches: StateFlow<Resource<List<CompetitionModel>>> = refreshAllMatches.
        onStart { emit(false) }
        .flatMapLatest { refresh ->
            goRepositoryInterface.getAllMatches(refresh)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Empty
        )



//    init {
//
//        viewModelScope.launch {
//            goDao.getAllCompetitions().map {
//                if (it.isNotEmpty()){
//                    _allMatchesState.value = GoEvent.MatchesSuccessEvent(it)
//                }else{
//                    getAllMatches()
//                }
//            }
//        }
//
//
////        if (goDao.getAllCompetitions().map {  }){
////            _allMatches.value = GoEvent.MatchesSuccessEvent(CompetitionResponseModel(competitions))
////        }else{
////            getAllMatches()
////        }
//
////        goDao.getAllCompetitions().flatMapLatest { competitions ->
////            if (competitions.isNotEmpty()){
////                _allMatches.value = GoEvent.MatchesSuccessEvent(CompetitionResponseModel(competitions))
////            }else{
////                getAllMatches()
////            }
////        }
//    }

//    fun getMatchesFromDatabase(){
//        goDao.getAllCompetitions().flatMapLatest { competitions ->
//            if (competitions.isNotEmpty()){
//                _allMatches.value = GoEvent.MatchesSuccessEvent(CompetitionResponseModel(competitions))
//            }else{
//                getAllMatches()
//            }
//        }
//    }

    fun refreshAllMatches(){
        viewModelScope.launch {
            refreshAllMatches.emit(true)
        }


//        viewModelScope.launch {
//
//            goRepositoryInterface.getAllMatches(refresh = true)
//
////            _allMatchesState.send(GoEvent.Loading)
////            try {
////                when(val getMatchesResponse = goRepositoryInterface.getNetworkMatches()){
////                    is Resource.Success -> {
////                        _allMatchesState.value = GoEvent.MatchesSuccessEvent()
////                    }
////                    is Resource.Error -> {
////                        _allMatchesState.value = GoEvent.Error(getMatchesResponse.message!!)
////                        _allMatchesChannel.send(GoEvent.Error(getMatchesResponse.message))
////                    }
////                    else -> Unit
////                }
////            }catch (e: Exception){
////                when(e){
////                    is IOException -> {
////                        _allMatchesState.value = GoEvent.Error("Weak Network")
////                        _allMatchesChannel.send(GoEvent.Error("Weak Network"))
////                    }
////                    else -> {
////                        _allMatchesState.value = GoEvent.Error(e.message!!)
////                        _allMatchesChannel.send(GoEvent.Error(e.message!!))
////                    }
////                }
////            }
//        }
    }


}