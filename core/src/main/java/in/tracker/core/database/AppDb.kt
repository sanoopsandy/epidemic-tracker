package `in`.tracker.core.database

import `in`.tracker.core.database.daos.CountryStatDao
import `in`.tracker.core.database.daos.WorldStatDao
import `in`.tracker.core.database.entities.CountryStat
import `in`.tracker.core.database.entities.WorldStat
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CountryStat::class, WorldStat::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun worldStatDao(): WorldStatDao
    abstract fun countryStatDao(): CountryStatDao
}