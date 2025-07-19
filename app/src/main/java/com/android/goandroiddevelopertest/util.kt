package com.android.goandroiddevelopertest

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