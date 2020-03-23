package `in`.tracker.epidemic.screens.details

import `in`.tracker.core.api.DataResult
import `in`.tracker.core.utils.showToast
import `in`.tracker.epidemic.R
import `in`.tracker.epidemic.screens.details.viewmodel.ChartDetailsViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_country_details.*


class CountryDetails : Fragment() {

    private val viewModel: ChartDetailsViewModel by lazy {
        ViewModelProvider(this).get(
            ChartDetailsViewModel::class.java
        )
    }
    val args: CountryDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        viewModel.getHistoricalData(args.countryName)
    }

    private fun setListeners() {
        viewModel.historicalCountryLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is DataResult.Success -> {
                    drawTotalCaseChart(result.data.dateArray, result.data)
                }
                is DataResult.Failure -> {
                    result.error.message?.let { showToast(it) }
                }
                is DataResult.Progress -> {
                }
            }
        })
    }

    private fun drawTotalCaseChart(timeStamp: List<String>, chartModel: ChartModel) {
        Log.i("Sanoop", "Inside success ${timeStamp.size}")
        val dataSets = arrayListOf<ILineDataSet>()
        val totalCaseDataSet = LineDataSet(chartModel.totalCasesModel.dataSets, "Total cases")
        totalCaseDataSet.color = ColorTemplate.VORDIPLOM_COLORS[0]
        totalCaseDataSet.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0])
        totalCaseDataSet.valueTextColor = resources.getColor(R.color.primaryTextColor)

        val totalRecoveredModel =
            LineDataSet(chartModel.totalRecoveredModel.dataSets, "Total Recovered")
        totalRecoveredModel.color = ColorTemplate.COLORFUL_COLORS[1]
        totalRecoveredModel.lineWidth = 1.2f
        totalRecoveredModel.setCircleColor(ColorTemplate.COLORFUL_COLORS[1])
        totalRecoveredModel.valueTextColor = resources.getColor(R.color.primaryTextColor)

        val totalDeathModel =
            LineDataSet(chartModel.totalDeathModel.dataSets, "Total Deaths")
        totalDeathModel.color = ColorTemplate.PASTEL_COLORS[0]
        totalDeathModel.setCircleColor(ColorTemplate.PASTEL_COLORS[0])
        totalDeathModel.lineWidth = 1.2f
        totalDeathModel.valueTextColor = resources.getColor(R.color.primaryTextColor)

        dataSets.add(totalCaseDataSet)
        dataSets.add(totalRecoveredModel)
        dataSets.add(totalDeathModel)

        setDataToChart(dataSets, timeStamp)

    }

    private fun setDataToChart(
        dataSets: List<ILineDataSet>,
        timeStamp: List<String>
    ) {
        detailChart.data = LineData(dataSets)

        detailChart.marker = CustomMarkerView(context!!, R.layout.custom_marker_view)

        val xAxis = detailChart.xAxis
        xAxis.setCenterAxisLabels(true)
        xAxis.setLabelCount(timeStamp.distinct().size, true)
        xAxis.isGranularityEnabled = true
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                if (value < 0 || value >= timeStamp.size) return ""
                Log.i("Sanoop", "Inside form -> ${value.toInt()}")
                return timeStamp[value.toInt()]
            }
        }

        detailChart.invalidate()
    }
}