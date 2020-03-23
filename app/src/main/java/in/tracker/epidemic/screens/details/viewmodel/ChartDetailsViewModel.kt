package `in`.tracker.epidemic.screens.details.viewmodel

import `in`.tracker.core.api.DataResult
import `in`.tracker.epidemic.datamanager.DashboardRepository
import `in`.tracker.epidemic.di.DashboardHandler
import `in`.tracker.epidemic.screens.details.ChartModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChartDetailsViewModel : ViewModel() {

    init {
        DashboardHandler.getDashboardComponent().inject(this)
    }

    @Inject
    lateinit var repo: DashboardRepository

    var historicalCountryLiveData: MediatorLiveData<DataResult<ChartModel>> =
        MediatorLiveData()

    fun getHistoricalData(country: String) {
        viewModelScope.launch {
            historicalCountryLiveData.addSource(repo.getCountryHistoryStat(country)) { newData ->
                historicalCountryLiveData.value = newData
            }
        }
    }
}