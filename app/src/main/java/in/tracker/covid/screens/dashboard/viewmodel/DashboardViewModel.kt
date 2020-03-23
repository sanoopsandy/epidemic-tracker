package `in`.tracker.covid.screens.dashboard.viewmodel

import `in`.tracker.core.api.DataResult
import `in`.tracker.core.database.entities.CountryStat
import `in`.tracker.core.database.entities.WorldStat
import `in`.tracker.covid.datamanager.DashboardRepository
import `in`.tracker.covid.di.DashboardHandler
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel : ViewModel() {

    init {
        DashboardHandler.getDashboardComponent().inject(this)
    }

    @Inject
    lateinit var repo: DashboardRepository

    var worldStatLiveData: MediatorLiveData<DataResult<WorldStat>> =
        MediatorLiveData()

    fun fetchWorldStatDetails() {
        viewModelScope.launch {
            worldStatLiveData.addSource(repo.getWorldStatData()) { newData ->
                worldStatLiveData.value = newData
            }
        }
    }
}