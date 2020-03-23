package `in`.tracker.covid.screens.country

import `in`.tracker.core.adapter.BaseRecyclerAdapter
import `in`.tracker.core.api.DataResult
import `in`.tracker.core.database.entities.CountryStat
import `in`.tracker.core.utils.navigateTo
import `in`.tracker.core.utils.showProgress
import `in`.tracker.core.utils.showToast
import `in`.tracker.covid.R
import `in`.tracker.covid.databinding.FragmentCountryCasesBinding
import `in`.tracker.covid.screens.country.viewmodel.CountryCasesViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_country_cases.*
import java.util.*

class CountryCasesFragment : Fragment() {

    private val adapter = BaseRecyclerAdapter()
    lateinit var binding: FragmentCountryCasesBinding

    private var adapterItems = listOf<CountryStat>()
    private val viewModel: CountryCasesViewModel by lazy {
        ViewModelProvider(this).get(
            CountryCasesViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryCasesBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAdapter()
        setListeners()
        viewModel.fetchAllCountryStat()
    }

    private fun setListeners() {
        viewModel.lastUpdatedTime.observe(viewLifecycleOwner, Observer { time ->
            binding.timeStamp = time
        })
        viewModel.countryStatListLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is DataResult.Success -> {
                    adapterItems = result.data
                    adapter.updateAdapterItems(adapterItems)
                }
                is DataResult.Failure -> {
                    result.error.message?.let { showToast(it) }
                }
                is DataResult.Progress -> {
                    progress.showProgress(result.loading)
                }
            }
        })
        edtCountryName.addTextChangedListener { edt ->
            adapter.updateAdapterItems(
                adapterItems
                    .filter {
                        it.country_name.toLowerCase(Locale.getDefault())
                            .contains(edt.toString().toLowerCase(Locale.getDefault()))
                    })
        }
    }

    private fun setAdapter() {
        adapter.layoutId = R.layout.row_country_stat
        adapter.items = listOf<CountryStat>()
        adapter.onCustomClickItemListener = { _, pos ->
            val countryStat = adapter.items[pos] as CountryStat
            Log.i("Sanoop", "Country name -> ${countryStat.country_name}")
            view?.navigateTo(
                CountryCasesFragmentDirections.actionCountryCasesFragmentToCountryDetails(
                    countryStat.country_name
                )
            )
        }
        rvCountryStat.adapter = adapter
    }
}