package `in`.tracker.covid.screens.splash

import `in`.tracker.core.utils.navigateTo
import `in`.tracker.covid.R
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Handler().postDelayed({
            view?.navigateTo(SplashFragmentDirections.actionSplashFragmentToDashboardFragment())
        }, 1500)
    }
}