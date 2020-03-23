package `in`.tracker.core.di

import `in`.tracker.core.prefs.CovidCorePref
import android.content.Context
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class])
interface CoreComponent {

    fun context(): Context

    fun retrofit(): Retrofit

    fun pref(): CovidCorePref

}