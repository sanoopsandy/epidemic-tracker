package `in`.tracker.epidemic.datamanager

import `in`.tracker.core.database.AppDb
import `in`.tracker.core.database.entities.CountryStat
import `in`.tracker.core.database.entities.WorldStat
import androidx.lifecycle.LiveData

class DashboardLocalHandler(
    private val appDb: AppDb
) : DashboardBlueprint.Local {

    override fun saveWorldStatDetails(worldStat: WorldStat) {
        appDb.worldStatDao().insertWorldStat(worldStat)
    }

    override fun getWorldStatDetails(): LiveData<WorldStat> {
        return appDb.worldStatDao().getWorldStatData()
    }

    override fun saveCountryStatDetails(countryStat: List<CountryStat>) {
        appDb.countryStatDao().insertCountryStats(countryStat)
    }

    override fun getCountryStatDetails(): LiveData<List<CountryStat>> {
        return appDb.countryStatDao().getCountryStatData()
    }

}