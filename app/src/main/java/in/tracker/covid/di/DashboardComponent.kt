package `in`.tracker.covid.di

import `in`.tracker.core.adapter.BaseRecyclerAdapter
import `in`.tracker.core.database.AppDb
import `in`.tracker.core.di.CoreComponent
import `in`.tracker.core.prefs.CovidCorePref
import `in`.tracker.covid.datamanager.DashboardBlueprint
import `in`.tracker.covid.datamanager.DashboardLocalHandler
import `in`.tracker.covid.datamanager.DashboardRemoteHandler
import `in`.tracker.covid.datamanager.DashboardRepository
import `in`.tracker.covid.network.DashboardApiService
import `in`.tracker.covid.screens.country.viewmodel.CountryCasesViewModel
import `in`.tracker.covid.screens.dashboard.viewmodel.DashboardViewModel
import `in`.tracker.covid.screens.details.viewmodel.ChartDetailsViewModel
import android.content.Context
import androidx.room.Room
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@DashboardScope
@Component(dependencies = [CoreComponent::class], modules = [DashboardModule::class])
interface DashboardComponent {
    fun inject(dashboardViewModel: DashboardViewModel)
    fun inject(dashboardViewModel: CountryCasesViewModel)
    fun inject(chartDetailsViewModel: ChartDetailsViewModel)
}

@Module
class DashboardModule {

    @DashboardScope
    @Provides
    fun getAdapter() = BaseRecyclerAdapter()

    @DashboardScope
    @Provides
    fun getCartRepository(
        local: DashboardBlueprint.Local,
        remote: DashboardBlueprint.Remote,
        pref: CovidCorePref
    ) = DashboardRepository(local, remote, pref)

    @DashboardScope
    @Provides
    fun getCartLocal(appDb: AppDb): DashboardBlueprint.Local = DashboardLocalHandler(appDb)

    @DashboardScope
    @Provides
    fun getCartRemote(service: DashboardApiService): DashboardBlueprint.Remote =
        DashboardRemoteHandler(service)

    /* Base provides dependencies */
    @DashboardScope
    @Provides
    fun appDB(context: Context): AppDb =
        Room.databaseBuilder(context, AppDb::class.java, "covid_app.db")
            .fallbackToDestructiveMigration()
            .build()

    @DashboardScope
    @Provides
    fun getDashboardService(retrofit: Retrofit): DashboardApiService =
        retrofit.create(DashboardApiService::class.java)
}