package com.android.goandroiddevelopertest.data.model

data class TeamModel(
    val id: Int,
    val shortName: String,
    val crest: String,
    val squad: List<SquadModel>?

)
