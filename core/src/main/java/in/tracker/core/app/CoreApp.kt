package `in`.tracker.core.app

import `in`.tracker.core.BuildConfig
import `in`.tracker.core.di.AppModule
import `in`.tracker.core.di.CoreComponent
import `in`.tracker.core.di.DaggerCoreComponent
import android.app.Application
import com.facebook.stetho.Stetho

class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDi()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initDi() {
        coreComponent = DaggerCoreComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}