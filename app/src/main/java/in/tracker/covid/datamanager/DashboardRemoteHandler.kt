package `in`.tracker.covid.datamanager

import `in`.tracker.core.database.entities.WorldStat
import `in`.tracker.covid.network.AllCountryStatResponse
import `in`.tracker.covid.network.CountryHistoryStatResponse
import `in`.tracker.covid.network.DashboardApiService

class DashboardRemoteHandler(
    private val service: DashboardApiService
) : DashboardBlueprint.Remote {
    override suspend fun getWorldStat(): WorldStat {
        return service.getWorldStat()
    }

    override suspend fun getAllCountryStat(): AllCountryStatResponse {
        return service.getAllCountryStat()
    }

    override suspend fun getCountryHistoryStat(country: String): CountryHistoryStatResponse {
        return service.getCountryHistoryStat(country)
    }


}