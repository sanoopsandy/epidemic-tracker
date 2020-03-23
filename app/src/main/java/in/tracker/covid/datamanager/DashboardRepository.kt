package `in`.tracker.covid.datamanager

import `in`.tracker.core.api.DataResult
import `in`.tracker.core.api.FetchDataHelper
import `in`.tracker.core.database.entities.CountryStat
import `in`.tracker.core.database.entities.WorldStat
import `in`.tracker.core.prefs.CovidCorePref
import `in`.tracker.core.utils.failure
import `in`.tracker.core.utils.getISTDateStringFromTime
import `in`.tracker.core.utils.loading
import `in`.tracker.core.utils.success
import `in`.tracker.covid.network.AllCountryStatResponse
import `in`.tracker.covid.screens.details.*
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.Entry
import com.google.firebase.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardRepository(
    private val local: DashboardBlueprint.Local,
    private val remote: DashboardBlueprint.Remote,
    private val pref: CovidCorePref
) : DashboardBlueprint.Repository {

    companion object {
        private val TAG = DashboardRepository::class.java.simpleName
        private val DBG = BuildConfig.DEBUG
    }

    val lastUpdatedTime = MutableLiveData<String>()
    private val chartHistoricData = MutableLiveData<DataResult<ChartModel>>()

    override suspend fun getWorldStatData(): LiveData<DataResult<WorldStat>> {
        return object : FetchDataHelper<WorldStat, WorldStat>() {

            override fun saveCallResult(model: WorldStat) {
                local.saveWorldStatDetails(model)
            }

            override fun loadFromDb(): LiveData<WorldStat> {
                return local.getWorldStatDetails()
            }

            override suspend fun createCall(): WorldStat {
                return remote.getWorldStat()
            }

        }.getAsLiveData()
    }

    override suspend fun getCountryStatData(): LiveData<DataResult<List<CountryStat>>> {
        return object : FetchDataHelper<List<CountryStat>, AllCountryStatResponse>() {

            override fun saveCallResult(model: AllCountryStatResponse) {
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        lastUpdatedTime.value = model.statistic_taken_at.getISTDateStringFromTime()
                    }
                }
                local.saveCountryStatDetails(model.countries_stat)
            }

            override fun loadFromDb(): LiveData<List<CountryStat>> {
                return local.getCountryStatDetails()
            }

            override suspend fun createCall(): AllCountryStatResponse {
                return remote.getAllCountryStat()
            }

        }.getAsLiveData()
    }

    override suspend fun getCountryHistoryStat(country: String): MutableLiveData<DataResult<ChartModel>> {
        chartHistoricData.loading(true)
        try {
            val result = remote.getCountryHistoryStat(country)
            Log.i("Sanoop", "Got result !!")
            Log.i("Sanoop", "size -> ${result.stat_by_country.size}")
            val totalCasesModelDataSet = arrayListOf<Entry>()
            val totalDeathModelDataSet = arrayListOf<Entry>()
            val totalRecoveredModelDataSet = arrayListOf<Entry>()
            val totalNewCasesModelDataSet = arrayListOf<Entry>()
            val totalNewDeathModelDataSet = arrayListOf<Entry>()
            val dateArray = arrayListOf<String>()
            result.stat_by_country.forEachIndexed { pos, data ->
                dateArray.add(data.formattedTimeStamp)
                val totalCase = if (data.total_cases.replace(",", "").isEmpty())
                    0f
                else
                    data.total_cases.replace(",", "").toFloat()

                val recoveredCase = if (data.total_recovered.replace(",", "").isEmpty())
                    0f
                else
                    data.total_recovered.replace(",", "").toFloat()

                val deathCase = if (data.total_deaths.replace(",", "").isEmpty())
                    0f
                else
                    data.total_deaths.replace(",", "").toFloat()

                val newCase = if (data.new_cases.replace(",", "").isEmpty())
                    0f
                else
                    data.new_cases.replace(",", "").toFloat()

                val newDeath = if (data.new_deaths.replace(",", "").isEmpty())
                    0f
                else
                    data.new_deaths.replace(",", "").toFloat()

                totalCasesModelDataSet.add(
                    Entry(
                        pos.toFloat(),
                        totalCase
                    )
                )
                totalDeathModelDataSet.add(
                    Entry(
                        pos.toFloat(),
                        deathCase
                    )
                )
                totalRecoveredModelDataSet.add(
                    Entry(
                        pos.toFloat(),
                        recoveredCase
                    )
                )
                totalNewCasesModelDataSet.add(
                    Entry(
                        pos.toFloat(),
                        newCase
                    )
                )
                totalNewDeathModelDataSet.add(
                    Entry(
                        pos.toFloat(),
                        newDeath
                    )
                )
            }
            Log.i("Sanoop", "totalCasesModelDataSet -> ${totalCasesModelDataSet.size}")
            chartHistoricData.success(
                ChartModel(
                    TotalCasesModel(totalCasesModelDataSet),
                    TotalDeathModel(totalDeathModelDataSet),
                    TotalRecoveredModel(totalRecoveredModelDataSet),
                    TotalNewCasesModel(totalNewCasesModelDataSet),
                    TotalNewDeathModel(totalNewDeathModelDataSet),
                    dateArray
                )
            )
        } catch (ex: Exception) {
            chartHistoricData.failure(ex)
        }
        return chartHistoricData
    }
}