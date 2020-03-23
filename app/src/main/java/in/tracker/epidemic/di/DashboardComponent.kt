package `in`.tracker.epidemic.di

import `in`.tracker.core.adapter.BaseRecyclerAdapter
import `in`.tracker.core.database.AppDb
import `in`.tracker.core.di.CoreComponent
import `in`.tracker.core.prefs.CovidCorePref
import `in`.tracker.epidemic.datamanager.DashboardBlueprint
import `in`.tracker.epidemic.datamanager.DashboardLocalHandler
import `in`.tracker.epidemic.datamanager.DashboardRemoteHandler
import `in`.tracker.epidemic.datamanager.DashboardRepository
import `in`.tracker.epidemic.network.DashboardApiService
import `in`.tracker.epidemic.screens.country.viewmodel.CountryCasesViewModel
import `in`.tracker.epidemic.screens.dashboard.viewmodel.DashboardViewModel
import `in`.tracker.epidemic.screens.details.viewmodel.ChartDetailsViewModel
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