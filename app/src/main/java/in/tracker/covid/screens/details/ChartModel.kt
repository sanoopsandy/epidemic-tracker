package `in`.tracker.covid.screens.details

import com.github.mikephil.charting.data.Entry

data class TotalCasesModel(val dataSets: List<Entry>)
data class TotalDeathModel(val dataSets: List<Entry>)
data class TotalRecoveredModel(val dataSets: List<Entry>)
data class TotalNewCasesModel(val dataSets: List<Entry>)
data class TotalNewDeathModel(val dataSets: List<Entry>)

data class ChartModel(
    val totalCasesModel: TotalCasesModel,
    val totalDeathModel: TotalDeathModel,
    val totalRecoveredModel: TotalRecoveredModel,
    val totalNewCasesModel: TotalNewCasesModel,
    val totalNewDeathModel: TotalNewDeathModel,
    val dateArray: List<String>
)