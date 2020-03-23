package `in`.tracker.epidemic

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
        with(supportActionBar!!) {
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_nav_back)
            title = "Dashboard"
        }
        listenNavChanges()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun listenNavChanges() {
        findNavController(R.id.nav_host_fragment)
            .addOnDestinationChangedListener { _, destination, _ ->
                destinationChanged(destination)
            }
    }

    private fun destinationChanged(destination: NavDestination) {
        when (destination.id) {
            R.id.splashFragment -> {
                supportActionBar?.hide()
            }
            R.id.dashboardFragment -> {
                supportActionBar?.title = "Dashboard"
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.show()
            }
            R.id.countryCasesFragment -> {
                supportActionBar?.title = "Affected Country Stat"
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.show()
            }
        }
    }

    override fun onBackPressed() {
        when (findNavController(R.id.nav_host_fragment).currentDestination?.id) {
            R.id.dashboardFragment -> {
                finish()
            }
            else -> super.onBackPressed()
        }
    }
}
