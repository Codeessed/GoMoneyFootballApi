package com.android.goandroiddevelopertest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.goandroiddevelopertest.data.model.CompetitionModel

@Database(
    entities = [CompetitionModel::class],
    version = 1
)
abstract  class GoDatabase: RoomDatabase() {

    abstract val dao: GoDao

}