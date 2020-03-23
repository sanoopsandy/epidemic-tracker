package `in`.tracker.covid.screens.dashboard

import `in`.tracker.core.api.DataResult
import `in`.tracker.core.utils.navigateTo
import `in`.tracker.core.utils.showProgress
import `in`.tracker.core.utils.showToast
import `in`.tracker.covid.databinding.FragmentDashboardBinding
import `in`.tracker.covid.screens.dashboard.viewmodel.DashboardViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

    private val viewModel: DashboardViewModel by lazy {
        ViewModelProvider(this).get(
            DashboardViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        viewModel.fetchWorldStatDetails()
    }

    private fun setListeners() {
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            viewModel.fetchWorldStatDetails()
        }
        viewModel.worldStatLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is DataResult.Success -> {
                    binding.model = result.data
                }
                is DataResult.Failure -> {
                    result.error.message?.let { showToast(it) }
                }
                is DataResult.Progress -> {
                    swipeRefresh.isRefreshing = result.loading
                    progress.showProgress(result.loading)
                }
            }
        })
        btnSeeDetails.setOnClickListener {
            view?.navigateTo(DashboardFragmentDirections.actionDashboardFragmentToCountryCasesFragment())
        }
    }

}