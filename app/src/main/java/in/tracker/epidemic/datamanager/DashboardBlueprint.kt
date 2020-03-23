package `in`.tracker.epidemic.datamanager

import `in`.tracker.core.api.DataResult
import `in`.tracker.core.database.entities.CountryStat
import `in`.tracker.core.database.entities.WorldStat
import `in`.tracker.epidemic.network.AllCountryStatResponse
import `in`.tracker.epidemic.network.CountryHistoryStatResponse
import `in`.tracker.epidemic.screens.details.ChartModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface DashboardBlueprint {

    interface Repository {
        suspend fun getWorldStatData(): LiveData<DataResult<WorldStat>>
        suspend fun getCountryStatData(): LiveData<DataResult<List<CountryStat>>>
        suspend fun getCountryHistoryStat(country: String): MutableLiveData<DataResult<ChartModel>>
    }

    interface Remote {
        suspend fun getWorldStat(): WorldStat
        suspend fun getAllCountryStat(): AllCountryStatResponse
        suspend fun getCountryHistoryStat(country: String): CountryHistoryStatResponse
    }

    interface Local {
        fun saveWorldStatDetails(worldStat: WorldStat)
        fun getWorldStatDetails(): LiveData<WorldStat>
        fun saveCountryStatDetails(countryStat: List<CountryStat>)
        fun getCountryStatDetails(): LiveData<List<CountryStat>>
    }

}