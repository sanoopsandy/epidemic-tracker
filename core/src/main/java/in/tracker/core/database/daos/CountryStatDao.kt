package `in`.tracker.core.database.daos

import `in`.tracker.core.database.entities.CountryStat
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CountryStatDao {

    @Query("SELECT * FROM country_stat")
    fun getCountryStatData(): LiveData<List<CountryStat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryStats(countryStatList: List<CountryStat>)

    @Delete
    fun delete(countryStatList: CountryStat)
}