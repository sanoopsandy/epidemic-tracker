package `in`.tracker.core.database.daos

import `in`.tracker.core.database.entities.WorldStat
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WorldStatDao {

    @Query("SELECT * FROM world_stat")
    fun getWorldStatData(): LiveData<WorldStat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorldStat(worldStat: WorldStat)

    @Delete
    fun delete(worldStat: WorldStat)
}