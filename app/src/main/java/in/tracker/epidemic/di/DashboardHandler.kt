package `in`.tracker.epidemic.di

import `in`.tracker.core.app.CoreApp

object DashboardHandler {

    private var DashboardComponent: DashboardComponent? = null

    /*
    * Fetch the Dashboard component dependency
    * */
    fun getDashboardComponent(): DashboardComponent {
        if (DashboardComponent == null) {
            DashboardComponent =
                DaggerDashboardComponent.builder().coreComponent(CoreApp.coreComponent).build()
        }
        return DashboardComponent as DashboardComponent
    }

    fun destroyDashboardComponent() {
        DashboardComponent = null
    }

}