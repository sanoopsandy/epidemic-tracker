package `in`.tracker.epidemic.screens.country.viewmodel

import `in`.tracker.core.api.DataResult
import `in`.tracker.core.database.entities.CountryStat
import `in`.tracker.core.prefs.CovidCorePref
import `in`.tracker.epidemic.datamanager.DashboardRepository
import `in`.tracker.epidemic.di.DashboardHandler
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryCasesViewModel : ViewModel() {

    init {
        DashboardHandler.getDashboardComponent().inject(this)
    }

    @Inject
    lateinit var repo: DashboardRepository

    @Inject
    lateinit var pref: CovidCorePref

    val lastUpdatedTime = repo.lastUpdatedTime

    var countryStatListLiveData: MediatorLiveData<DataResult<List<CountryStat>>> =
        MediatorLiveData()

    fun fetchAllCountryStat() {
        viewModelScope.launch {
            countryStatListLiveData.addSource(repo.getCountryStatData()) { newData ->
                countryStatListLiveData.value = newData
            }
        }
    }
}