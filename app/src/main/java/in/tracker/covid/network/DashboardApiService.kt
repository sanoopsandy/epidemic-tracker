package `in`.tracker.covid.network

import `in`.tracker.core.database.entities.WorldStat
import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardApiService {

    @GET("/coronavirus/worldstat.php")
    suspend fun getWorldStat(): WorldStat

    @GET("/coronavirus/cases_by_country.php")
    suspend fun getAllCountryStat(): AllCountryStatResponse

    @GET("/coronavirus/cases_by_particular_country.php")
    suspend fun getCountryHistoryStat(@Query("country") country: String): CountryHistoryStatResponse

}