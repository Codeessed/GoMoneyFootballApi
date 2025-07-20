package com.android.goandroiddevelopertest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.goandroiddevelopertest.data.model.CompetitionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCompetitions(competitions: List<CompetitionModel>)

    @Query("SELECT * FROM competition_table ORDER BY name ASC")
    fun getAllCompetitions(): Flow<List<CompetitionModel>>


}