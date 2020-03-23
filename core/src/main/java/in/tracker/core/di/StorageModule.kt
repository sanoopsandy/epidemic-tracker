package `in`.tracker.core.di

import `in`.tracker.core.prefs.CovidCorePref
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun providesCovidSharedPreferences(context: Context): CovidCorePref {
        return CovidCorePref(context)
    }
}