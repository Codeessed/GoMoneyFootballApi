package com.android.goandroiddevelopertest

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface BottomNavigationController {
    fun showBottomNavigation()
    fun hideBottomNavigation()
}

interface OnCompetitionItemClickListener{
    fun onCompetitionClick(position: Int)
}

interface OnTeamItemClickListener{
    fun onTeamClick(position: Int)
}

object Constants{
    const val base_url = "https://api.football-data.org/v4/"
}

object FlowObserver {
    fun <T> Fragment.observer(flow: Flow<T>, collect: suspend(T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collect)
            }
        }
    }
}

sealed class Resource<T>(val data: T?, val message: String?) {
    class Success<T>(data: T?) : Resource<T>(data, null)
    class Error<T>(message: String?) : Resource<T>(null, message)
}