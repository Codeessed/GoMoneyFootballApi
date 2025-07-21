package com.android.goandroiddevelopertest

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.goandroiddevelopertest.db.entities.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

interface BottomNavigationController {
    fun showBottomNavigation()
    fun hideBottomNavigation()
}

interface OnCompetitionItemClickListener{
    fun onCompetitionClick(competitionId: Int)
}

interface OnTeamItemClickListener{
    fun onTeamClick(team: Team)
}

object Constants{
    const val base_url = "https://api.football-data.org/v4/"

    fun String.fromIsoToString(incomingPattern: String, outgoingPattern: String): String {
        return try {
            SimpleDateFormat(incomingPattern, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("GMT")
            }.parse(this)?.let {
                SimpleDateFormat(outgoingPattern, Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("GMT")
                }.format(it)
            }?:this
        }catch (ex: ParseException){
            ex.toString()
        }
    }
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

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(data, null)
    class Error<T>(data: T?, message: String?) : Resource<T>(data, message)
    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()
}